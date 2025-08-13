package ao.com.wundu.user_category_limit.controller;

import ao.com.wundu.user_category_limit.dtos.UserCategoryLimitRequest;
import ao.com.wundu.user_category_limit.dtos.UserCategoryLimitResponse;
import ao.com.wundu.user_category_limit.service.UserCategoryLimitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/limits")
public class UserCategoryLimitController {

    @Autowired
    private UserCategoryLimitService limitService;

    @PutMapping
    public ResponseEntity<UserCategoryLimitResponse> setLimit(@RequestBody @Valid UserCategoryLimitRequest request) {
        return ResponseEntity.ok(limitService.setLimit(request));
    }

    @GetMapping("/{userId}/{categoryId}")
    public ResponseEntity<UserCategoryLimitResponse> getLimit(@PathVariable String userId,
                                                              @PathVariable String categoryId) {
        return ResponseEntity.ok(limitService.getLimit(userId, categoryId));
    }
}
