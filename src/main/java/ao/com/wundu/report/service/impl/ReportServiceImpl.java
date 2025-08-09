package ao.com.wundu.report.service.impl;

import ao.com.wundu.report.dtos.CategoryReportRequest;
import ao.com.wundu.report.dtos.CategoryReportResponse;
import ao.com.wundu.report.repository.TransactionReportRepository;
import ao.com.wundu.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private TransactionReportRepository transactionReportRepository;

    @Override
    public CategoryReportResponse getCategorySpendingReport(CategoryReportRequest request) {
        Double totalCategorySpent = transactionReportRepository.sumSpentByUserCategoryAndMonth(
            request.userId(), request.categoryId(), request.year(), request.month());

        Double totalMonthSpent = transactionReportRepository.sumTotalSpentByUserAndMonth(
            request.userId(), request.year(), request.month());

        double percentage = 0;
        if (totalMonthSpent != null && totalMonthSpent != 0) {
            percentage = (totalCategorySpent / totalMonthSpent) * 100;
        }

        return new CategoryReportResponse(
            request.userId(),
            request.categoryId(),
            totalCategorySpent != null ? Math.abs(totalCategorySpent) : 0,
            totalMonthSpent != null ? Math.abs(totalMonthSpent) : 0,
            percentage
        );
    }
}
