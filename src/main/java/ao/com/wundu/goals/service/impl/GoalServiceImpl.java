package ao.com.wundu.goals.service.impl;

import ao.com.wundu.goals.dtos.GoalProgressDTO;
import ao.com.wundu.goals.dtos.GoalRequestDTO;
import ao.com.wundu.goals.dtos.GoalResponseDTO;
import ao.com.wundu.goals.entity.FinancialGoal;
import ao.com.wundu.goals.entity.GoalProgress;
import ao.com.wundu.goals.enums.GoalStatus;
import ao.com.wundu.goals.mapper.GoalMapper;
import ao.com.wundu.goals.repository.GoalProgressRepository;
import ao.com.wundu.goals.repository.GoalRepository;
import ao.com.wundu.goals.service.GoalService;
import ao.com.wundu.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private GoalProgressRepository progressRepository;

    @Autowired
    private GoalMapper mapper;

    private Double calculatePercentage(BigDecimal current, BigDecimal target) {
        if (current == null || target == null || target.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        return current.divide(target, 4, RoundingMode.HALF_UP)
                      .multiply(BigDecimal.valueOf(100))
                      .doubleValue();
    }

    @Override
    @Transactional
    public GoalResponseDTO create(String userId, GoalRequestDTO request) {
        if (request.endDate().isBefore(request.startDate()) || request.endDate().isEqual(request.startDate())) {
            throw new ResourceNotFoundException(
                "Data final deve ser maior que a data inicial",
                HttpStatus.BAD_REQUEST);
        }

        FinancialGoal goal = mapper.toEntity(request);
        goal.setUserId(userId);
        goal = goalRepository.save(goal);

        // auditService.record("GOAL_CREATED", userId, goal.getId(),
        //         "title=" + goal.getTitle() + ", target=" + goal.getTargetAmount());

        return mapper.toResponse(
            goal,
            calculatePercentage(goal.getCurrentAmount(),
            goal.getTargetAmount()));
    }

    @Override
    @Transactional
    public GoalResponseDTO update(String id, String userId, GoalRequestDTO request) {
        FinancialGoal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com id=" + id));

        if (!goal.getUserId().equals(userId)) {
            throw new ResourceNotFoundException(
                "Acesso negado para editar esta meta",
                HttpStatus.FORBIDDEN);
        }

        if (request.endDate().isBefore(goal.getStartDate()) || request.endDate().isEqual(goal.getStartDate())) {
            throw new ResourceNotFoundException(
                "Data final deve ser maior que a data inicial",
                HttpStatus.BAD_REQUEST);
        }

        goal.setTitle(request.title());
        goal.setDescription(request.description());
        goal.setTargetAmount(request.targetAmount());
        goal.setEndDate(request.endDate());

        if (goal.getCurrentAmount().compareTo(goal.getTargetAmount()) >= 0) {
            goal.setStatus(GoalStatus.DONE);
        } else {
            goal.setStatus(GoalStatus.ACTIVE);
        }

        goal = goalRepository.save(goal);

        // auditService.record("GOAL_UPDATED", userId, goal.getId(),
        //         "title=" + goal.getTitle() + ", target=" + goal.getTargetAmount());

        return mapper.toResponse(
            goal,
            calculatePercentage(goal.getCurrentAmount(),
            goal.getTargetAmount()));
    }

    @Override
    public GoalResponseDTO findById(String id, String userId) {
        FinancialGoal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com id=" + id));

        if (!goal.getUserId().equals(userId)) {
            throw new ResourceNotFoundException(
                "Acesso negado a esta meta",
                HttpStatus.FORBIDDEN);
        }

        Double percentage = calculatePercentage(goal.getCurrentAmount(), goal.getTargetAmount());
        return mapper.toResponse(goal, percentage);
    }

    @Override
    public List<GoalResponseDTO> findByUser(String userId, String status) {
        List<FinancialGoal> goals = goalRepository.findByUserId(userId);

        if (status != null) {
            goals = goals.stream()
                    .filter(g -> g.getStatus().name().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        return goals.stream()
                .map(g -> mapper.toResponse(g, calculatePercentage(g.getCurrentAmount(), g.getTargetAmount())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GoalProgressDTO addProgress(String goalId, String userId, BigDecimal amount, LocalDate progressDate) {
        FinancialGoal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com id=" + goalId));

        if (!goal.getUserId().equals(userId)) {
            throw new ResourceNotFoundException(
                "Acesso negado para adicionar progresso",
                HttpStatus.FORBIDDEN);
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResourceNotFoundException(
                "Valor de progresso inválido",
                HttpStatus.BAD_REQUEST);
        }

        if (progressDate == null) {
            throw new ResourceNotFoundException(
                "Data do progresso é obrigatória",
                HttpStatus.BAD_REQUEST);
        }

        GoalProgress progress = mapper.toProgressEntity(amount, progressDate);
        progress.setGoal(goal);

        progress = progressRepository.save(progress);

        goal.increaseCurrentAmount(amount);
        if (goal.getCurrentAmount().compareTo(goal.getTargetAmount()) >= 0) {
            goal.setStatus(GoalStatus.DONE);
        }
        goalRepository.save(goal);

        return mapper.toProgressDTO(progress);
    }

    @Override
    public List<GoalProgressDTO> listProgress(String goalId, String userId) {
        FinancialGoal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com id=" + goalId));
        if (!goal.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Acesso negado", HttpStatus.FORBIDDEN);
        }
        return progressRepository.findByGoal_IdOrderByProgressDateDesc(goalId)
                .stream()
                .map(mapper::toProgressDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(String id, String userId) {
        FinancialGoal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com id=" + id));
        if (!goal.getUserId().equals(userId)) {
            throw new ResourceNotFoundException(
                "Acesso negado para remover meta",
                HttpStatus.FORBIDDEN);
        }

        goal.setStatus(GoalStatus.ARCHIVED);
        goalRepository.save(goal);

        // auditService.record("GOAL_ARCHIVED", userId, goal.getId(),
        //         "archived by user");
    }
}
