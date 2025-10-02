package ao.com.wundu.transaction.specification;

import ao.com.wundu.transaction.entity.Transaction;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTransactionTemplate {

    @And({
        @Spec(path = "transactionDate", spec = Between.class),
        @Spec(path = "categoryId", spec = Equal.class),         
        @Spec(path = "status", spec = Equal.class),             
        @Spec(path = "description", spec = LikeIgnoreCase.class) 
    })
    public interface TransactionSpec extends Specification<Transaction> {}
}
