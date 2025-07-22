package ao.com.wundu.core.usecases.transaction;

import ao.com.wundu.infra.persistence.dtos.TransactionResponse;
import java.util.List;

public interface ListTransactionByUserAndCategoryUseCase {
    List<TransactionResponse> execute(String userId, String categoryId);
}
