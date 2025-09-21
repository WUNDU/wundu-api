package ao.com.wundu.goals.repository;

import ao.com.wundu.goals.entity.GoalProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalProgressRepository extends JpaRepository<GoalProgress, String> {

    List<GoalProgress> findByGoal_IdOrderByProgressDateDesc(String goalId);

}
