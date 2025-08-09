package ao.com.wundu.transaction.dtos;

import java.time.LocalDate;

import ao.com.wundu.category.dto.CategoryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TransactionRequest(

        // @NotBlank(message = "O tipo da transação é obrigatório")
        // @Pattern(regexp = "^(income|expense)$", message = "O tipo deve ser 'income'
        // ou 'expense'")
        String type,

        @Size(max = 50, message = "A fonte da transação deve ter no máximo 50 caracteres")
        String source,

        @NotNull(message = "O valor da transação é obrigatório")
        @Positive(message = "O valor deve ser maior que zero") 
        Double amount,

        @NotBlank(message = "O ID do usuário é obrigatório")
        String userId,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres") 
        String description,

        @NotNull(message = "A data da transação é obrigatória") @Schema(example = "2025-07-05") 
        LocalDate transactionDate,

        CategoryRequest category

) {
}
