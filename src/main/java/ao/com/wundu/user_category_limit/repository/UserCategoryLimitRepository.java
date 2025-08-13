package ao.com.wundu.user_category_limit.repository;

import ao.com.wundu.user_category_limit.entity.UserCategoryLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCategoryLimitRepository extends JpaRepository<UserCategoryLimit, String> {
    Optional<UserCategoryLimit> findByUserIdAndCategoryId(String userId, String categoryId);
}
