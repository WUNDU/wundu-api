package ao.com.wundu.transaction.specification;

import ao.com.wundu.transaction.entity.Transaction;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import java.time.LocalDate;

@And({
    @Spec(path = "category.id", spec = Equal.class),
    @Spec(path = "status", spec = Equal.class),
    @Spec(path = "transactionDate", params = {"startDate", "endDate"}, spec = Between.class),
    @Spec(path = "userId", spec = Equal.class)
})
public interface TransactionSpec extends java.io.Serializable, org.springframework.data.jpa.domain.Specification<Transaction> {}
