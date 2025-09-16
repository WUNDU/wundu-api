package ao.com.wundu.transaction.dtos;

import java.time.LocalDate;

import ao.com.wundu.category.dto.CategoryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TransactionRequest(

        @NotBlank(message = "O tipo da transação é obrigatório")
        @Schema(example = "income", description = "Tipo da transação: income ou expense")
        String type,

        @Size(max = 50, message = "A fonte da transação deve ter no máximo 50 caracteres")
        @Schema(example = "PDF", description = "Fonte ou origem da transação")
        String source,

        @NotNull(message = "O valor da transação é obrigatório")
        @Positive(message = "O valor deve ser maior que zero") 
        @Schema(example = "2500.75", description = "Valor da transação")
        Double amount,

        @NotBlank(message = "O ID do usuário é obrigatório")
        @Schema(example = "user-123", description = "ID do usuário dono da transação")
        String userId,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        @Schema(example = "Pagamento da renda", description = "Descrição detalhada da transação") 
        String description,

        @NotNull(message = "A data da transação é obrigatória") 
        @Schema(example = "2025-07-05", description = "Data em que a transação ocorreu") 
        LocalDate transactionDate,

        @Schema(example = "pending", description = "Status da transação (pending, completed, failed)")
        String status,

        CategoryRequest category
) {
}
