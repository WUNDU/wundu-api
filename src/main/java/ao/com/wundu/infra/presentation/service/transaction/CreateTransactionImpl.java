package ao.com.wundu.infra.presentation.service.transaction;

import ao.com.wundu.core.exception.ResourceNotFoundException;
import ao.com.wundu.core.usecases.transaction.CreateTransactionUseCase;
import ao.com.wundu.infra.persistence.dtos.TransactionRequest;
import ao.com.wundu.infra.persistence.dtos.TransactionResponse;
import ao.com.wundu.infra.persistence.mappers.TransactionMapper;
import ao.com.wundu.infra.presentation.entities.Category;
import ao.com.wundu.infra.presentation.entities.Transaction;
import ao.com.wundu.infra.presentation.entities.User;
import ao.com.wundu.infra.presentation.repositories.CategoryRepository;
import ao.com.wundu.infra.presentation.repositories.TransactionRepository;
import ao.com.wundu.infra.presentation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTransactionImpl implements CreateTransactionUseCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public TransactionResponse execute(TransactionRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Usuário com id=%s não encontrado", request.userId()))
                );

        Transaction transaction = TransactionMapper.toEntity(request);

        if (request.category() != null) {
            String categoryName = request.category().name().trim();

            Category category = categoryRepository.findByName(categoryName)
                    .orElseGet(() -> {
                        Category newCategory = new Category(
                                categoryName,
                                request.category().description() != null ? request.category().description() : ""
                        );
                        return categoryRepository.save(newCategory);
                    });

            transaction.setCategory(category);
        }
        transaction = transactionRepository.save(transaction);

        return TransactionMapper.toResponse(transaction);
    }
}
