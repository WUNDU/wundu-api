package ao.com.wundu.goals.repository;

import ao.com.wundu.goals.entity.FinancialGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<FinancialGoal, String> {

    List<FinancialGoal> findByUserId(String userId);

}
