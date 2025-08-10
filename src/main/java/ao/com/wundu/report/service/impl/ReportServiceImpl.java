package ao.com.wundu.report.service.impl;

import ao.com.wundu.category.repository.CategoryRepository;
import ao.com.wundu.usuario.repository.UserRepository;
import ao.com.wundu.report.dtos.CategoryReportRequest;
import ao.com.wundu.report.dtos.CategoryReportResponse;
import ao.com.wundu.report.dtos.CategorySpendDto;
import ao.com.wundu.report.dtos.MonthlyReportResponse;
import ao.com.wundu.report.repository.TransactionReportRepository;
import ao.com.wundu.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
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

        LocalDate date = parseAndValidateDate(request.date());
        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());

        Double totalCategorySpent = transactionReportRepository.sumSpentByUserCategoryAndMonth(
                request.userId(), request.categoryId(), startOfMonth, endOfMonth);

        Double totalMonthSpent = transactionReportRepository.sumTotalSpentByUserAndMonth(
                request.userId(), startOfMonth, endOfMonth);

        double percentage = 0;
        if (totalMonthSpent != null && totalMonthSpent != 0) {
            percentage = (totalCategorySpent / totalMonthSpent) * 100;
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

        LocalDate date = parseAndValidateDate(dateStr);
        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());

        // total do mês
        Double totalMonthSpent = transactionReportRepository.sumTotalSpentByUserAndMonth(userId, startOfMonth, endOfMonth);
        if (totalMonthSpent == null) totalMonthSpent = 0.0;

        // por categoria
        List<CategorySpendDto> grouped = transactionReportRepository.sumSpentGroupedByCategory(userId, startOfMonth, endOfMonth);

        // monta lista com percentagens
        List<MonthlyReportResponse.CategorySpendWithPercentage> categories = new ArrayList<>();
        for (CategorySpendDto dto : grouped) {
            double pct = 0;
            if (totalMonthSpent != 0) {
                pct = (dto.totalSpent() / totalMonthSpent) * 100;
            }
            categories.add(new MonthlyReportResponse.CategorySpendWithPercentage(
                    dto.categoryId(),
                    dto.categoryName(),
                    dto.totalSpent(),
                    pct
            ));
        }

        String monthLabel = String.format("%04d-%02d", date.getYear(), date.getMonthValue());

        return new MonthlyReportResponse(userId, monthLabel, totalMonthSpent, categories);
    }

    // helpers
    private void ensureUserExists(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
    }

    private void ensureCategoryExists(String categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Categoria não encontrada");
        }
    }

    private LocalDate parseAndValidateDate(String dateStr) {
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Data inválida. Use o formato YYYY-MM-DD");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data não pode ser no futuro");
        }
        return date;
    }
}
