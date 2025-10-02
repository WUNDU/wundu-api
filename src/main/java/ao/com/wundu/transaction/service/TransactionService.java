package ao.com.wundu.transaction.service;

import ao.com.wundu.transaction.dtos.TransactionRequest;
import ao.com.wundu.transaction.dtos.TransactionResponse;
import ao.com.wundu.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface TransactionService {

    TransactionResponse create(TransactionRequest request);

    TransactionResponse defineTransaction(String transactionId, String categoryName);

    TransactionResponse findById(String id);

    List<TransactionResponse> findByUserAndCategory(String categoryId);

    List<TransactionResponse> findByUser();

    Page<TransactionResponse> findAll(int page, int size);

    Page<TransactionResponse> findWithFilters(Specification<Transaction> spec, Pageable pageable);

}