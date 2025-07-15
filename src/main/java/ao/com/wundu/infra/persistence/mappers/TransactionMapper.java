package ao.com.wundu.infra.persistence.mappers;

import ao.com.wundu.infra.persistence.dtos.TransactionRequest;
import ao.com.wundu.infra.persistence.dtos.TransactionResponse;
import ao.com.wundu.infra.presentation.entities.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public final class TransactionMapper {

    private TransactionMapper() {}

    // 🔁 Converte DTO de entrada -> Entidade
    public static Transaction toEntity(TransactionRequest request) {
        Transaction transaction = new Transaction(
            request.amount(),
            request.description(),
            request.type(),
            request.userId()
        );
        transaction.setSource(request.source());
        return transaction;
    }

    // 🔁 Converte Entidade -> DTO de saída
    public static TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
            transaction.getId(),
            transaction.getType(),
            transaction.getSource(),
            transaction.getAmount(),
            transaction.getUserId(),
            transaction.getDescription(),
            transaction.getDateTime()
        );
    }

    // 🔁 Converte Lista de Entidades -> Lista de DTOs
    public static List<TransactionResponse> toList(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
