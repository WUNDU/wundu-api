package ao.com.wundu.transaction.dtos;

import java.time.LocalDate;

import ao.com.wundu.category.dto.CategoryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TransactionUpdate(

        @Schema(example = "income", description = "Tipo da transação: income ou expense")
        String type,

        @Size(max = 50, message = "A fonte da transação deve ter no máximo 50 caracteres")
        @Schema(example = "PDF", description = "Fonte ou origem da transação")
        String source,

        @Positive(message = "O valor deve ser maior que zero") 
        @Schema(example = "1500.50", description = "Valor da transação")
        Double amount,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        @Schema(example = "Compra de material de escritório", description = "Descrição detalhada da transação") 
        String description,

        @Schema(example = "completed", description = "Status da transação (pending, completed, failed)")
        String status,

        @Schema(example = "2025-08-15", description = "Data em que a transação ocorreu") 
        LocalDate transactionDate,

        CategoryRequest category
) {}
