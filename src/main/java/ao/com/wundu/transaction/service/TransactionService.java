package ao.com.wundu.transaction.service;

import org.springframework.data.jpa.domain.Specification;
import ao.com.wundu.transaction.entity.Transaction;
import ao.com.wundu.category.dto.DefineCategoryRequest;
import ao.com.wundu.transaction.dtos.TransactionRequest;
import ao.com.wundu.transaction.dtos.TransactionResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    TransactionResponse create(TransactionRequest request);

    TransactionResponse defineTransaction(String transactionId, DefineCategoryRequest request);

    TransactionResponse findById(String id);

    List<TransactionResponse> findByUserAndCategory(String categoryId);

    List<TransactionResponse> findByUser();

    Page<TransactionResponse> findAll(int page, int size);

    Page<TransactionResponse> findWithFilters(Specification<Transaction> spec, int page, int size)

}
