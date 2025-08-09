package ao.com.wundu.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ao.com.wundu.transaction.entity.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUserId(String userId);

    List<Transaction> findByUserIdAndCategory_Id(String userId, String categoryId);
}