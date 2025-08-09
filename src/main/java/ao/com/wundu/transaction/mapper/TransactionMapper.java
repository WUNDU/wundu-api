package ao.com.wundu.transaction.mapper;

import ao.com.wundu.category.dto.CategoryResponse;
import ao.com.wundu.category.mapper.CategoryMapper;
import ao.com.wundu.transaction.dtos.TransactionRequest;
import ao.com.wundu.transaction.dtos.TransactionResponse;
import ao.com.wundu.transaction.entity.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class TransactionMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    private TransactionMapper() {
    }

    // Converte DTO de entrada -> Entidade
    public Transaction toEntity(TransactionRequest request) {
        Transaction transaction = new Transaction(
                request.amount(),
                request.description(),
                request.type(),
                request.userId(),
                request.transactionDate());
        transaction.setSource(request.source());
        transaction.setTransactionDate(request.transactionDate());
        transaction.setCategory(null);
        return transaction;
    }

    // Converte Entidade -> DTO de saída
    public  TransactionResponse toResponse(Transaction transaction) {
        CategoryResponse categoryResponse = null;
        if (transaction.getCategory() != null) {
            categoryResponse =   categoryMapper.toResponse(transaction.getCategory());
        }
        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getSource(),
                transaction.getAmount(),
                transaction.getUserId(),
                transaction.getDescription(),
                transaction.getDateTime(),
                transaction.getTransactionDate(),
                categoryResponse);
    }

    // Converte Lista de Entidades -> Lista de DTOs
    public List<TransactionResponse> toList(List<Transaction> transactions) {
        return transactions.stream()
                .map( transaction -> toResponse(transaction) )
                .collect(Collectors.toList());
    }
}
