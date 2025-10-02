package ao.com.wundu.transaction.service.impl;

import ao.com.wundu.category.entity.Category;
import ao.com.wundu.category.repository.CategoryRepository;
import ao.com.wundu.exception.ResourceNotFoundException;
import ao.com.wundu.jwt.JwtUserDetails;
import ao.com.wundu.transaction.dtos.TransactionRequest;
import ao.com.wundu.transaction.dtos.TransactionResponse;
import ao.com.wundu.transaction.entity.Transaction;
import ao.com.wundu.transaction.mapper.TransactionMapper;
import ao.com.wundu.transaction.repository.TransactionRepository;
import ao.com.wundu.transaction.service.TransactionService;
import ao.com.wundu.usuario.entity.User;
import ao.com.wundu.usuario.enums.Role;
import ao.com.wundu.usuario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private JwtUserDetails getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (JwtUserDetails) auth.getPrincipal();
    }

    @Override
    public TransactionResponse create(TransactionRequest request) {
        JwtUserDetails userDetails = getAuthenticatedUser();
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Transaction transaction = transactionMapper.toEntity(request);
        transaction.setUserId(user.getId());

        if (request.category() != null) {
            String categoryName = request.category().name().trim();
            Category category = categoryRepository.findByName(categoryName)
                    .orElseGet(() -> categoryRepository.save(new Category(categoryName)));
            transaction.setCategory(category);
        }

        transaction = transactionRepository.save(transaction);
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionResponse defineTransaction(String transactionId, String categoryName) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada."));

        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> categoryRepository.save(new Category(categoryName)));
        transaction.setCategory(category);

        transactionRepository.save(transaction);
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionResponse findById(String id) {
        JwtUserDetails userDetails = getAuthenticatedUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transação com id=" + id + " não encontrada"));
        if (!userDetails.getRole().equals(Role.ADMIN.name()) &&
                !transaction.getUserId().equals(userDetails.getId())) {
            throw new ResourceNotFoundException("Acesso negado à transação id = " + id, HttpStatus.FORBIDDEN);
        }
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public List<TransactionResponse> findByUserAndCategory(String categoryId) {
        JwtUserDetails userDetails = getAuthenticatedUser();
        List<Transaction> transactions = transactionRepository.findByUserIdAndCategory_Id(userDetails.getId(), categoryId);
        return transactionMapper.toList(transactions);
    }

    @Override
    public List<TransactionResponse> findByUser() {
        JwtUserDetails userDetails = getAuthenticatedUser();
        List<Transaction> transactions = transactionRepository.findByUserId(userDetails.getId());
        return transactionMapper.toList(transactions);
    }

    @Override
    public Page<TransactionResponse> findAll(int page, int size) {
        JwtUserDetails userDetails = getAuthenticatedUser();
        PageRequest pageable = PageRequest.of(page, size);

        Page<Transaction> transactions;
        if (userDetails.getRole().equals(Role.ADMIN.name())) {
            transactions = transactionRepository.findAll(pageable);
        } else {
            transactions = transactionRepository.findByUserId(userDetails.getId(), pageable);
        }

        List<TransactionResponse> responseList = transactionMapper.toList(transactions.getContent());
        return new PageImpl<>(responseList, pageable, transactions.getTotalElements());
    }

    @Override
    public Page<TransactionResponse> findWithFilters(Specification<Transaction> spec, Pageable pageable) {
        JwtUserDetails userDetails = getAuthenticatedUser();

        if (userDetails.getRole().equals(Role.CLIENTE.name())) {
            Specification<Transaction> userSpec = (root, query, builder) ->
                    builder.equal(root.get("userId"), userDetails.getId());
            spec = (spec == null) ? userSpec : spec.and(userSpec);
        }

        Page<Transaction> transactions = transactionRepository.findAll(spec, pageable);

        List<TransactionResponse> responseList = transactionMapper.toList(transactions.getContent());
        return new PageImpl<>(responseList, pageable, transactions.getTotalElements());
    }
}
