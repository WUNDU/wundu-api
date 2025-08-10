package ao.com.wundu.report.repository;

import ao.com.wundu.transaction.entity.Transaction;
import ao.com.wundu.report.dtos.CategorySpendDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionReportRepository extends JpaRepository<Transaction, String> {

    /**
     * Soma dos gastos de um usuário numa categoria entre start e end.
     * Tratamos como "gasto" quando:
     *  - amount < 0  (então somamos -amount para ficar positivo)
     *  - ou LOWER(type) LIKE '%exp%' (ex.: expend, expense) -> somamos amount (pos/neg)
     */
    @Query("""
        SELECT COALESCE(SUM(
            CASE
                WHEN t.amount < 0 THEN -t.amount
                WHEN LOWER(t.type) LIKE '%exp%' THEN ABS(t.amount)
                ELSE 0
            END
        ), 0)
        FROM Transaction t
        WHERE t.userId = :userId
          AND t.category.id = :categoryId
          AND t.transactionDate BETWEEN :start AND :end
    """)
    Double sumSpentByUserCategoryAndMonth(@Param("userId") String userId,
                                          @Param("categoryId") String categoryId,
                                          @Param("start") LocalDate start,
                                          @Param("end") LocalDate end);

    /**
     * Soma total dos gastos do usuário no mês (usar mesma lógica de detectar gasto).
     */
    @Query("""
        SELECT COALESCE(SUM(
            CASE
                WHEN t.amount < 0 THEN -t.amount
                WHEN LOWER(t.type) LIKE '%exp%' THEN ABS(t.amount)
                ELSE 0
            END
        ), 0)
        FROM Transaction t
        WHERE t.userId = :userId
          AND t.transactionDate BETWEEN :start AND :end
    """)
    Double sumTotalSpentByUserAndMonth(@Param("userId") String userId,
                                       @Param("start") LocalDate start,
                                       @Param("end") LocalDate end);

    /**
     * Agrupa por categoria e retorna (categoryId, categoryName, totalSpentInCategory)
     * Usamos um DTO projection (CategorySpendDto).
     */
    @Query("""
        SELECT new ao.com.wundu.report.dtos.CategorySpendDto(
            t.category.id,
            t.category.name,
            COALESCE(SUM(
                CASE
                    WHEN t.amount < 0 THEN -t.amount
                    WHEN LOWER(t.type) LIKE '%exp%' THEN ABS(t.amount)
                    ELSE 0
                END
            ), 0)
        )
        FROM Transaction t
        WHERE t.userId = :userId
          AND t.transactionDate BETWEEN :start AND :end
        GROUP BY t.category.id, t.category.name
        ORDER BY 3 DESC
    """)
    List<CategorySpendDto> sumSpentGroupedByCategory(@Param("userId") String userId,
                                                     @Param("start") LocalDate start,
                                                     @Param("end") LocalDate end);
}
