package ao.com.wundu.core.usecases.transaction;

import ao.com.wundu.infra.persistence.dtos.DefineCategoryRequest;
import ao.com.wundu.infra.persistence.dtos.TransactionResponse;

public interface DefineTransactionCategoryUseCase {
    TransactionResponse execute(String transactionId, DefineCategoryRequest request);
}
