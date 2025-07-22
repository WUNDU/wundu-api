package ao.com.wundu.core.usecases.transaction;

import ao.com.wundu.infra.persistence.dtos.TransactionResponse;

public interface GetTransactionByIdUseCase {
    TransactionResponse execute(String id);
}
