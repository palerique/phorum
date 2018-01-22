package br.com.ph.phorum.domain.repository;

import br.com.ph.phorum.domain.entities.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findOneByNameIgnoreCase(String name);
}
