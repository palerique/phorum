package br.com.ph.phorum.domain.service;

import br.com.ph.phorum.domain.entities.Category;
import br.com.ph.phorum.domain.repository.CategoryRepository;
import br.com.ph.phorum.infra.dto.CategoryDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category createCategory(CategoryDTO categoryDTO) {
    return categoryRepository.save(Category.builder()
        .name(categoryDTO.getName())
        .build());
  }

  public void delete(Long id) {
    log.debug("Deleted Category #{}", id);
    categoryRepository.delete(categoryRepository.getOne(id));
  }
}
