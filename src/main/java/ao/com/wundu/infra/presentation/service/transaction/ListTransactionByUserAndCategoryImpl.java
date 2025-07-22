package ao.com.wundu.infra.presentation.service.transaction;

import ao.com.wundu.core.exception.ResourceNotFoundException;
import ao.com.wundu.core.usecases.transaction.ListTransactionByUserAndCategoryUseCase;
import ao.com.wundu.infra.persistence.dtos.TransactionResponse;
import ao.com.wundu.infra.persistence.mappers.TransactionMapper;
import ao.com.wundu.infra.presentation.entities.Transaction;
import ao.com.wundu.infra.presentation.entities.User;
import ao.com.wundu.infra.presentation.entities.Category;
import ao.com.wundu.infra.presentation.repositories.TransactionRepository;
import ao.com.wundu.infra.presentation.repositories.UserRepository;
import ao.com.wundu.infra.presentation.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTransactionByUserAndCategoryImpl implements ListTransactionByUserAndCategoryUseCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<TransactionResponse> execute(String userId, String categoryId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário com id=" + userId + " não encontrado"));

        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria com id=" + categoryId + " não encontrada"));

        List<Transaction> transactions = transactionRepository.findByUserIdAndCategory_Id(user.getId(), category.getId());

        return TransactionMapper.toList(transactions);
    }
}
