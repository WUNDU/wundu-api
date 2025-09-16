package ao.com.wundu.category.specification;

import ao.com.wundu.category.entity.Category;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationCategoryTemplate {

    @And({
            @Spec(path = "name", spec = LikeIgnoreCase.class),
            @Spec(path = "type", spec = Equal.class),
            @Spec(path = "isActive", spec = Equal.class)
    })
    public interface CategorySpec extends Specification<Category> {}
}
