package ao.com.wundu.infra.persistence.mappers;

import ao.com.wundu.infra.persistence.dtos.CategoryResponse;
import ao.com.wundu.infra.persistence.dtos.TransactionRequest;
import ao.com.wundu.infra.persistence.dtos.TransactionResponse;
import ao.com.wundu.infra.presentation.entities.Transaction;
import ao.com.wundu.infra.persistence.mappers.CategoryMapper;

import java.util.List;
import java.util.stream.Collectors;

public final class TransactionMapper {

    private TransactionMapper() {
    }

    // Converte DTO de entrada -> Entidade
    public static Transaction toEntity(TransactionRequest request) {
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
    public static TransactionResponse toResponse(Transaction transaction) {
        CategoryResponse categoryResponse = null;
        if (transaction.getCategory() != null) {
            categoryResponse = CategoryMapper.toResponse(transaction.getCategory());
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
    public static List<TransactionResponse> toList(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
