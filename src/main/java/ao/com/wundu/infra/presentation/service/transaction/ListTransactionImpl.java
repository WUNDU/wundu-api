package ao.com.wundu.infra.presentation.service.transaction;

import ao.com.wundu.core.usecases.transaction.ListTransactionUseCase;
import ao.com.wundu.infra.persistence.dtos.TransactionResponse;
import ao.com.wundu.infra.persistence.mappers.TransactionMapper;
import ao.com.wundu.infra.presentation.entities.Transaction;
import ao.com.wundu.infra.presentation.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTransactionImpl implements ListTransactionUseCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<TransactionResponse> execute() {
        List<Transaction> transactions = transactionRepository.findAll();
        return TransactionMapper.toList(transactions);
    }
}
