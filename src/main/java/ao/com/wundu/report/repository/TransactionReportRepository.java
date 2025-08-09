package ao.com.wundu.report.repository;

import ao.com.wundu.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionReportRepository extends JpaRepository<Transaction, String> {

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t
        WHERE t.userId = :userId
          AND t.category.id = :categoryId
          AND FUNCTION('YEAR', t.transactionDate) = :year
          AND FUNCTION('MONTH', t.transactionDate) = :month
          AND t.amount < 0
    """)
    Double sumSpentByUserCategoryAndMonth(@Param("userId") String userId,
                                          @Param("categoryId") String categoryId,
                                          @Param("year") Integer year,
                                          @Param("month") Integer month);

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t
        WHERE t.userId = :userId
          AND FUNCTION('YEAR', t.transactionDate) = :year
          AND FUNCTION('MONTH', t.transactionDate) = :month
          AND t.amount < 0
    """)
    Double sumTotalSpentByUserAndMonth(@Param("userId") String userId,
                                       @Param("year") Integer year,
                                       @Param("month") Integer month);
}
