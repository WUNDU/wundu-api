package ao.com.wundu.transaction.service;

import ao.com.wundu.category.dto.DefineCategoryRequest;
import ao.com.wundu.transaction.dtos.TransactionRequest;
import ao.com.wundu.transaction.dtos.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse create(TransactionRequest request);
    TransactionResponse defineTransaction(String transactionId, DefineCategoryRequest request);
    TransactionResponse findById(String id);
    List<TransactionResponse> findByUserAndCategory(String userId, String categoryId);
    List<TransactionResponse> findByUser(String userId);
    List<TransactionResponse> findAll();
}
