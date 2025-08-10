package ao.com.wundu.report.service;

import ao.com.wundu.report.dtos.CategoryReportRequest;
import ao.com.wundu.report.dtos.CategoryReportResponse;
import ao.com.wundu.report.dtos.MonthlyReportResponse;

public interface ReportService {
    CategoryReportResponse getCategorySpendingReport(CategoryReportRequest request);
    MonthlyReportResponse getMonthlySpendingReport(String userId, String date);
}
