package ao.com.wundu.infra.presentation.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ao.com.wundu.core.usecases.transaction.DefineTransactionCategoryUseCase;
import ao.com.wundu.infra.presentation.entities.Category;
import ao.com.wundu.infra.presentation.entities.Transaction;
import ao.com.wundu.core.exception.ResourceNotFoundException;
import ao.com.wundu.infra.persistence.dtos.DefineCategoryRequest;
import ao.com.wundu.infra.persistence.dtos.TransactionResponse;
import ao.com.wundu.infra.persistence.mappers.TransactionMapper;
import ao.com.wundu.infra.presentation.repositories.CategoryRepository;
import ao.com.wundu.infra.presentation.repositories.TransactionRepository;

@Service
public class DefineTransactionCategoryImpl implements DefineTransactionCategoryUseCase {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public TransactionResponse execute(String transactionId, DefineCategoryRequest request) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada."));

        if (request.description() != null) {
            transaction.setDescription(request.description());
        }

        Category category = categoryRepository.findByName(request.categoryName())
                .orElseGet(() -> {
                    Category newCategory = new Category(request.categoryName());
                    return categoryRepository.save(newCategory);
                });

        transaction.setCategory(category);
        transactionRepository.save(transaction);

        return TransactionMapper.toResponse(transaction);
    }
}
