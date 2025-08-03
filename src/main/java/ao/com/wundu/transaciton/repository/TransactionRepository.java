package ao.com.wundu.transaciton.repository;

import ao.com.wundu.transaciton.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUserId(String userId);

    List<Transaction> findByUserIdAndCategory_Id(String userId, String categoryId);
}