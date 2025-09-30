package ao.com.wundu.goals.mapper;

import ao.com.wundu.goals.dtos.GoalProgressDTO;
import ao.com.wundu.goals.dtos.GoalRequestDTO;
import ao.com.wundu.goals.dtos.GoalResponseDTO;
import ao.com.wundu.goals.entity.FinancialGoal;
import ao.com.wundu.goals.entity.GoalProgress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GoalMapper {

    public FinancialGoal toEntity(GoalRequestDTO req) {
        FinancialGoal goal = new FinancialGoal();
        goal.setTitle(req.title());
        goal.setDescription(req.description());
        goal.setType(req.type());
        goal.setTargetAmount(req.targetAmount());
        goal.setStartDate(req.startDate());
        goal.setEndDate(req.endDate());
        return goal;
    }

    public GoalResponseDTO toResponse(FinancialGoal goal, Double percentage) {
        List<GoalProgressDTO> progress = goal.getProgress().stream()
                .map(this::toProgressDTO)
                .collect(Collectors.toList());

        return new GoalResponseDTO(
                goal.getId(),
                goal.getUserId(),
                goal.getTitle(),
                goal.getDescription(),
                goal.getType(),
                goal.getTargetAmount(),
                goal.getCurrentAmount(),
                percentage,
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getStatus(),
                goal.getCreatedAt(),
                progress
        );
    }

    public GoalProgressDTO toProgressDTO(GoalProgress p) {
        return new GoalProgressDTO(
                p.getId(),
                p.getAmount(),
                p.getProgressDate(),
                p.getCreatedAt()
        );
    }

    public GoalProgress toProgressEntity(java.math.BigDecimal amount, java.time.LocalDate progressDate) {
        return new GoalProgress(amount, progressDate);
    }
}
