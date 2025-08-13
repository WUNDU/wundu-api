package ao.com.wundu.user_category_limit.dtos;

public record UserCategoryLimitResponse(
    String id,
    String userId,
    String categoryId,
    Double monthlyLimit
) {}
