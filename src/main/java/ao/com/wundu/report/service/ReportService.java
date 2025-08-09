package ao.com.wundu.report.service;

import ao.com.wundu.report.dtos.CategoryReportRequest;
import ao.com.wundu.report.dtos.CategoryReportResponse;

public interface ReportService {
    CategoryReportResponse getCategorySpendingReport(CategoryReportRequest request);
}
