package ao.com.wundu.transaction.repository;

import ao.com.wundu.transaction.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TransactionSpecifications {

    public static Specification<Transaction> hasUserId(String userId) {
        return (root, query, builder) -> builder.equal(root.get("userId"), userId);
    }

    public static Specification<Transaction> hasCategoryId(String categoryId) {
        return (root, query, builder) ->
                categoryId == null ? builder.conjunction() : builder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Transaction> hasStatus(String status) {
        return (root, query, builder) ->
                status == null ? builder.conjunction() : builder.equal(root.get("status"), status);
    }

    public static Specification<Transaction> betweenDates(LocalDate startDate, LocalDate endDate) {
        return (root, query, builder) -> {
            if (startDate != null && endDate != null) {
                return builder.between(root.get("transactionDate"), startDate, endDate);
            }
            return builder.conjunction();
        };
    }
}
