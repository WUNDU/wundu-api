package ao.com.wundu.goals.service;

import ao.com.wundu.goals.dtos.GoalProgressDTO;
import ao.com.wundu.goals.dtos.GoalRequestDTO;
import ao.com.wundu.goals.dtos.GoalResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface GoalService {

    GoalResponseDTO create(String userId, GoalRequestDTO request);

    GoalResponseDTO update(String id, String userId, GoalRequestDTO request);

    GoalResponseDTO findById(String id, String userId);

    List<GoalResponseDTO> findByUser(String userId);

    GoalProgressDTO addProgress(String goalId, String userId, BigDecimal amount, LocalDate progressDate);

    List<GoalProgressDTO> listProgress(String goalId, String userId);

    void delete(String id, String userId);
}
