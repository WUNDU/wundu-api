package ao.com.wundu.transaciton.service.impl;

import ao.com.wundu.category.dto.DefineCategoryRequest;
import ao.com.wundu.category.entity.Category;
import ao.com.wundu.category.repository.CategoryRepository;
import ao.com.wundu.exception.ResourceNotFoundException;
import ao.com.wundu.transaciton.dtos.TransactionRequest;
import ao.com.wundu.transaciton.dtos.TransactionResponse;
import ao.com.wundu.transaciton.entity.Transaction;
import ao.com.wundu.transaciton.mapper.TransactionMapper;
import ao.com.wundu.transaciton.repository.TransactionRepository;
import ao.com.wundu.transaciton.service.TransactionService;
import ao.com.wundu.usuario.entity.User;
import ao.com.wundu.usuario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public TransactionResponse create(TransactionRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Usuário com id=%s não encontrado", request.userId())));

        Transaction transaction = transactionMapper.toEntity(request);

        if (request.category() != null) {
            String categoryName = request.category().name().trim();

            Category category = categoryRepository.findByName(categoryName)
                    .orElseGet(() -> {
                        Category newCategory = new Category(categoryName);
                        return categoryRepository.save(newCategory);
                    });

            transaction.setCategory(category);
        }
        transaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionResponse defineTransaction(String transactionId, DefineCategoryRequest request) {
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

        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionResponse findById(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transação com id=" + id + " não encontrada"));
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public List<TransactionResponse> findByUserAndCategory(String userId, String categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id=" + userId + " não encontrado"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria com id=" + categoryId + " não encontrada"));

        List<Transaction> transactions = transactionRepository.findByUserIdAndCategory_Id(user.getId(), category.getId());

        return transactionMapper.toList(transactions);
    }

    @Override
    public List<TransactionResponse> findByUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id=" + userId + " não encontrado"));

        List<Transaction> transactions = transactionRepository.findByUserId(user.getId());

        return transactionMapper.toList(transactions);
    }

    @Override
    public List<TransactionResponse> findAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactionMapper.toList(transactions);
    }
}
