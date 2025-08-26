package ao.com.wundu.user_category_limit.service;

import ao.com.wundu.user_category_limit.dtos.UserCategoryLimitRequest;
import ao.com.wundu.user_category_limit.dtos.UserCategoryLimitResponse;

public interface UserCategoryLimitService {
    UserCategoryLimitResponse setLimit(UserCategoryLimitRequest request);
    UserCategoryLimitResponse getLimit(String userId, String categoryId);
}
