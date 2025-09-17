package ao.com.wundu.transaction.dtos;

import ao.com.wundu.category.dto.CategoryResponse;
import ao.com.wundu.transaction.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponse(

        @Schema(description = "ID único da transação", example = "uuid-123")
        String id,

        @Schema(description = "Tipo da transação: income ou expense", example = "income")
        String type,

        @Schema(description = "Fonte ou origem da transação", example = "PDF")
        String source,

        @Schema(description = "Valor da transação", example = "2500.75")
        Double amount,

        @Schema(description = "ID do usuário dono da transação", example = "user-123")
        String userId,

        @Schema(description = "Descrição detalhada da transação", example = "Pagamento da renda")
        String description,

        @Schema(description = "Status da transação", example = "PENDING")
        TransactionStatus status,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Schema(description = "Data em que a transação ocorreu", example = "2025-07-05")
        LocalDate transactionDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @Schema(description = "Data e hora em que a transação foi criada", example = "2025-07-05T15:30:00")
        LocalDateTime createdAt,

        CategoryResponse category
) {}
