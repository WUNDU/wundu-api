package ao.com.wundu.user_category_limit.service.impl;

import ao.com.wundu.user_category_limit.dtos.UserCategoryLimitRequest;
import ao.com.wundu.user_category_limit.dtos.UserCategoryLimitResponse;
import ao.com.wundu.category.entity.Category;
import ao.com.wundu.user_category_limit.entity.UserCategoryLimit;
import ao.com.wundu.category.repository.CategoryRepository;
import ao.com.wundu.user_category_limit.repository.UserCategoryLimitRepository;
import ao.com.wundu.user_category_limit.service.UserCategoryLimitService;
import ao.com.wundu.usuario.entity.User;
import ao.com.wundu.usuario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCategoryLimitServiceImpl implements UserCategoryLimitService {

    @Autowired
    private UserCategoryLimitRepository limitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public UserCategoryLimitResponse setLimit(UserCategoryLimitRequest request) {
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Category category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        UserCategoryLimit limit = limitRepository.findByUserIdAndCategoryId(user.getId(), category.getId())
            .orElse(null);

        if (limit != null) {
            limit.setMonthlyLimit(request.monthlyLimit());
        } else {
            limit = new UserCategoryLimit();
            limit.setUser(user);
            limit.setCategory(category);
            limit.setMonthlyLimit(request.monthlyLimit());
        }
        limitRepository.save(limit);
        return new UserCategoryLimitResponse(
            limit.getId(), user.getId(), category.getId(), limit.getMonthlyLimit()
        );
    }

    @Override
    public UserCategoryLimitResponse getLimit(String userId, String categoryId) {
        return limitRepository.findByUserIdAndCategoryId(userId, categoryId)
            .map(limit -> new UserCategoryLimitResponse(
                limit.getId(), limit.getUser().getId(), limit.getCategory().getId(), limit.getMonthlyLimit()
            ))
            .orElseThrow(() -> new IllegalArgumentException("Limite não encontrado"));
    }
}
