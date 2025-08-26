package ao.com.wundu.report.service.impl;

import ao.com.wundu.category.repository.CategoryRepository;
import ao.com.wundu.usuario.repository.UserRepository;
import ao.com.wundu.report.dtos.CategoryReportRequest;
import ao.com.wundu.report.dtos.CategoryReportResponse;
import ao.com.wundu.report.dtos.CategorySpendDto;
import ao.com.wundu.report.dtos.MonthlyReportResponse;
import ao.com.wundu.report.repository.TransactionReportRepository;
import ao.com.wundu.report.service.ReportService;
import ao.com.wundu.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private TransactionReportRepository transactionReportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryReportResponse getCategorySpendingReport(CategoryReportRequest request) {
        ensureUserExists(request.userId());
        ensureCategoryExists(request.categoryId());

        LocalDate endDate = parseAndValidateDate(request.date());
        LocalDate startDate = endDate.minusMonths(1);

        Double totalCategorySpent = transactionReportRepository.sumSpentByUserCategoryAndMonth(
                request.userId(), request.categoryId(), startDate, endDate);

        Double totalMonthSpent = transactionReportRepository.sumTotalSpentByUserAndMonth(
                request.userId(), startDate, endDate);

        double percentage = 0;
        if (totalMonthSpent != null && totalMonthSpent != 0) {
            percentage = ((totalCategorySpent / totalMonthSpent) * 10000.0) / 100.0;
        }

        return new CategoryReportResponse(
                request.userId(),
                request.categoryId(),
                totalCategorySpent != null ? totalCategorySpent : 0,
                totalMonthSpent != null ? totalMonthSpent : 0,
                percentage
        );
    }

    @Override
    public MonthlyReportResponse getMonthlySpendingReport(String userId, String dateStr) {
        ensureUserExists(userId);

        LocalDate endDate = parseAndValidateDate(dateStr);
        LocalDate startDate = endDate.minusMonths(1);

        Double totalMonthSpent = transactionReportRepository.sumTotalSpentByUserAndMonth(userId, startDate, endDate);
        if (totalMonthSpent == null) totalMonthSpent = 0.0;

        List<CategorySpendDto> grouped = transactionReportRepository.sumSpentGroupedByCategory(userId, startDate, endDate);

        List<MonthlyReportResponse.CategorySpendWithPercentage> categories = new ArrayList<>();
        for (CategorySpendDto dto : grouped) {
            double pct = 0;
            if (totalMonthSpent != 0) {
                pct = Math.round((dto.totalSpent() / totalMonthSpent) * 10000.0) / 100.0;
            }
            categories.add(new MonthlyReportResponse.CategorySpendWithPercentage(
                    dto.categoryId(),
                    dto.categoryName(),
                    dto.totalSpent(),
                    pct
            ));
        }

        String monthLabel = String.format("%04d-%02d", endDate.getYear(), endDate.getMonthValue());
        return new MonthlyReportResponse(userId, monthLabel, totalMonthSpent, categories);
    }

    // Helpers
    private void ensureUserExists(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
    }

    private void ensureCategoryExists(String categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Categoria não encontrada");
        }
    }

    private LocalDate parseAndValidateDate(String dateStr) {
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Data inválida. Use o formato YYYY-MM-DD", HttpStatus.BAD_REQUEST);
        }
        if (date.isAfter(LocalDate.now())) {
            throw new ResourceNotFoundException("A data não pode ser no futuro", HttpStatus.BAD_REQUEST);
        }
        return date;
    }
}
