package ao.com.wundu.core.usecases.transaction;

import java.util.List;

import ao.com.wundu.infra.persistence.dtos.TransactionResponse;

public interface ListTransactionUseCase {
    List<TransactionResponse> execute();   
}
