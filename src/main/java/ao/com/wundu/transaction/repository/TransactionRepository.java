package ao.com.wundu.transaction.repository;

import ao.com.wundu.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String>, JpaSpecificationExecutor<Transaction> {

    List<Transaction> findByUserIdAndCategory_Id(String userId, String categoryId);

    List<Transaction> findByUserId(String userId);

    Page<Transaction> findByUserId(String userId, Pageable pageable);
}
