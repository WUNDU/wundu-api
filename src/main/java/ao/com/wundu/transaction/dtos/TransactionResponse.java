package ao.com.wundu.transaction.dtos;

import ao.com.wundu.category.dto.CategoryResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponse(
    String id,
    String type,
    String source,
    Double amount,
    String userId,
    String description,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime dateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate transactionDate,
    CategoryResponse category
) {}
