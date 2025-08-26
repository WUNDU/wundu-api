package ao.com.wundu.report.dtos;

public record CategoryReportResponse(
    String userId,
    String categoryId,
    Double totalSpentInCategory,
    Double totalSpentInMonth,
    Double percentageInCategory
) {}
