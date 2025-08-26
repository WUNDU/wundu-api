package ao.com.wundu.report.dtos;

public record CategorySpendDto(
    String categoryId,
    String categoryName,
    Double totalSpent
) {}
