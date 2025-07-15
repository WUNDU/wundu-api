package ao.com.wundu.infra.presentation.service.transaction;

import ao.com.wundu.core.exception.ResourceNotFoundException;
import ao.com.wundu.core.usecases.transaction.ListTransactionByUserUseCase;
import ao.com.wundu.infra.persistence.dtos.TransactionResponse;
import ao.com.wundu.infra.persistence.mappers.TransactionMapper;
import ao.com.wundu.infra.presentation.entities.Transaction;
import ao.com.wundu.infra.presentation.entities.User;
import ao.com.wundu.infra.presentation.repositories.TransactionRepository;
import ao.com.wundu.infra.presentation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTransactionByUserImpl implements ListTransactionByUserUseCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<TransactionResponse> execute(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id=" + userId + " não encontrado"));

        List<Transaction> transactions = transactionRepository.findByUserId(user.getId());

        return TransactionMapper.toList(transactions);
    }
}
