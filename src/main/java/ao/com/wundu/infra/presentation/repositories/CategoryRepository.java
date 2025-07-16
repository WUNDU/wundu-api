package ao.com.wundu.infra.presentation.repositories;

import ao.com.wundu.infra.presentation.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByName(String name);
}
