package ao.com.wundu.core.usecases.transaction;

import ao.com.wundu.infra.persistence.dtos.TransactionResponse;

import java.util.List;

public interface ListTransactionByUserUseCase {
    List<TransactionResponse> execute(String userId);
}
