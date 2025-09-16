package ao.com.wundu.transaction.repository;

import ao.com.wundu.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>,
                                               JpaSpecificationExecutor<Transaction> {

    List<Transaction> findByUserId(String userId);

    List<Transaction> findByUserIdAndCategory_Id(String userId, String categoryId);
}
