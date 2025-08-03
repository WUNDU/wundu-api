package ao.com.wundu.usuario.controller;

import ao.com.wundu.jwt.JwtToken;
import ao.com.wundu.jwt.JwtUserDetailsService;
import ao.com.wundu.usuario.dto.UserLogin;
import ao.com.wundu.usuario.dto.UserResponse;
import ao.com.wundu.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUserDetailsService detailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Autenticar na API", description = "Recurso de Autenticação na API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso e retorno de um bearer token",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Credencias inválidas",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campo(s) Inválido(s)",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody @Valid UserLogin login, HttpServletRequest request) {
        logger.info("Processo de autenticação pelo login {}", login.email());

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(login.email(), login.password());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(login.email());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            logger.warn("Bad Credentials from username '{}'", login.email());
        }

        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
    }
}
