package ao.com.wundu.report.dtos;

import java.util.List;

public record MonthlyReportResponse(
    String userId,
    String month,
    Double totalSpentInMonth,
    List<CategorySpendWithPercentage> categories
)
{
    public record CategorySpendWithPercentage(
        String categoryId,
        String categoryName,
        Double totalSpent,
        Double percentage)
        {}
}
