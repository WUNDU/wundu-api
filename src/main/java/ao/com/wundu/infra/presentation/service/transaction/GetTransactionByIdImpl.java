package ao.com.wundu.infra.presentation.service.transaction;

import ao.com.wundu.core.exception.ResourceNotFoundException;
import ao.com.wundu.core.usecases.transaction.GetTransactionByIdUseCase;
import ao.com.wundu.infra.persistence.dtos.TransactionResponse;
import ao.com.wundu.infra.persistence.mappers.TransactionMapper;
import ao.com.wundu.infra.presentation.entities.Transaction;
import ao.com.wundu.infra.presentation.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetTransactionByIdImpl implements GetTransactionByIdUseCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public TransactionResponse execute(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transação com id=" + id + " não encontrada"));
        return TransactionMapper.toResponse(transaction);
    }
}
