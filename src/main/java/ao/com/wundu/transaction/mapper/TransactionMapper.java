package ao.com.wundu.transaction.mapper;

import ao.com.wundu.category.dto.CategoryResponse;
import ao.com.wundu.category.mapper.CategoryMapper;
import ao.com.wundu.transaction.dtos.TransactionRequest;
import ao.com.wundu.transaction.dtos.TransactionResponse;
import ao.com.wundu.transaction.enums.TransactionStatus;
import ao.com.wundu.transaction.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    private TransactionMapper() {
    }

    public Transaction toEntity(TransactionRequest request) {
        Transaction transaction = new Transaction(
                request.amount(),
                request.description(),
                request.type(),
                request.userId(),
                request.transactionDate(),
                request.status() == null ? TransactionStatus.PENDING : request.status()
        );
        transaction.setSource(request.source());
        transaction.setCategory(null);
        return transaction;
    }

    public TransactionResponse toResponse(Transaction transaction) {
        CategoryResponse categoryResponse = null;
        if (transaction.getCategory() != null) {
            categoryResponse = categoryMapper.toResponse(transaction.getCategory());
        }
        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getSource(),
                transaction.getAmount(),
                transaction.getUserId(),
                transaction.getDescription(),
                transaction.getStatus(),
                transaction.getTransactionDate(),
                transaction.getCreatedAt(),
                categoryResponse
        );
    }

    public List<TransactionResponse> toList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
