package ao.com.wundu.goals.service.impl;

import ao.com.wundu.goals.dtos.GoalProgressDTO;
import ao.com.wundu.goals.dtos.GoalRequestDTO;
import ao.com.wundu.goals.dtos.GoalResponseDTO;
import ao.com.wundu.goals.entity.FinancialGoal;
import ao.com.wundu.goals.entity.GoalProgress;
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
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de metas.
 * Regras importantes:
 * - Apenas o dono (userId) pode criar/editar/deletar/adicionar progresso em sua meta.
 * - Ao adicionar progresso, atualizamos currentAmount e status (DONE se current >= target).
 */
@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private GoalProgressRepository progressRepository;

    @Autowired
    private GoalMapper mapper;

    @Override
    @Transactional
    public GoalResponseDTO create(String userId, GoalRequestDTO request) {
        FinancialGoal goal = mapper.toEntity(request);
        goal.setUserId(userId);
        goal = goalRepository.save(goal);
        return mapper.toResponse(goal);
    }

    @Override
    @Transactional
    public GoalResponseDTO update(String id, String userId, GoalRequestDTO request) {
        FinancialGoal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com id=" + id));

        if (!goal.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Acesso negado para editar esta meta", HttpStatus.FORBIDDEN);
        }

        // Atualiza campos permitidos
        goal.setTitle(request.title());
        goal.setDescription(request.description());
        goal.setType(request.type());
        goal.setTargetAmount(request.targetAmount());
        goal.setStartDate(request.startDate());
        goal.setEndDate(request.endDate());

        // se currentAmount >= novo target, atualiza status para DONE
        if (goal.getCurrentAmount().compareTo(goal.getTargetAmount()) >= 0) {
            goal.setStatus(ao.com.wundu.goals.enums.GoalStatus.DONE);
        }

        goal = goalRepository.save(goal);
        return mapper.toResponse(goal);
    }

    @Override
    public GoalResponseDTO findById(String id, String userId) {
        FinancialGoal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com id=" + id));
        if (!goal.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Acesso negado a esta meta", HttpStatus.FORBIDDEN);
        }
        return mapper.toResponse(goal);
    }

    @Override
    public List<GoalResponseDTO> findByUser(String userId) {
        List<FinancialGoal> goals = goalRepository.findByUserId(userId);
        return goals.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GoalProgressDTO addProgress(String goalId, String userId, BigDecimal amount, LocalDate progressDate) {
        FinancialGoal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com id=" + goalId));

        if (!goal.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Acesso negado para adicionar progresso", HttpStatus.FORBIDDEN);
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResourceNotFoundException("Valor de progresso inválido", HttpStatus.BAD_REQUEST);
        }

        if (progressDate == null) {
            throw new ResourceNotFoundException("Data do progresso é obrigatória", HttpStatus.BAD_REQUEST);
        }

        GoalProgress progress = mapper.toProgressEntity(amount, progressDate);
        progress.setGoal(goal);

        // salva progress
        progress = progressRepository.save(progress);

        // atualiza currentAmount e status
        goal.increaseCurrentAmount(amount);
        if (goal.getCurrentAmount().compareTo(goal.getTargetAmount()) >= 0) {
            goal.setStatus(ao.com.wundu.goals.enums.GoalStatus.DONE);
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
        List<GoalProgressDTO> list = progressRepository.findByGoal_IdOrderByProgressDateDesc(goalId)
                .stream()
                .map(mapper::toProgressDTO)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    @Transactional
    public void delete(String id, String userId) {
        FinancialGoal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com id=" + id));
        if (!goal.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Acesso negado para remover meta", HttpStatus.FORBIDDEN);
        }
        goalRepository.delete(goal);
    }
}
