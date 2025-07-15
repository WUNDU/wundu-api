package ao.com.wundu.core.usecases.transaction;

import ao.com.wundu.infra.persistence.dtos.TransactionRequest;
import ao.com.wundu.infra.persistence.dtos.TransactionResponse;

public interface CreateTransactionUseCase {
    TransactionResponse execute(TransactionRequest request);
}
