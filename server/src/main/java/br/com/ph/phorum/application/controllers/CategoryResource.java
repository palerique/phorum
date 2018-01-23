package br.com.ph.phorum.application.controllers;

import br.com.ph.phorum.application.controllers.error.BadRequestAlertException;
import br.com.ph.phorum.application.controllers.error.CategoryNameAlreadyUsedException;
import br.com.ph.phorum.application.controllers.util.HeaderUtil;
import br.com.ph.phorum.domain.entities.Category;
import br.com.ph.phorum.domain.repository.CategoryRepository;
import br.com.ph.phorum.domain.service.CategoryService;
import br.com.ph.phorum.infra.dto.CategoryDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class CategoryResource {

  private final CategoryRepository categoryRepository;
  private final CategoryService categoryService;

  public CategoryResource(CategoryRepository categoryRepository, CategoryService categoryService) {
    this.categoryRepository = categoryRepository;
    this.categoryService = categoryService;
  }

  @PostMapping("/categories")
  public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO categoryDTO)
      throws URISyntaxException {
    log.debug("REST request to save Category : {}", categoryDTO);

    if (categoryDTO.getId() != null) {
      throw new BadRequestAlertException("A new category cannot already have an ID",
          "categoryManagement",
          "idexists");
    } else if (categoryRepository.findOneByNameIgnoreCase(categoryDTO.getName()).isPresent()) {
      throw new CategoryNameAlreadyUsedException();
    } else {
      Category newCategory = categoryService.createCategory(categoryDTO);
      return ResponseEntity.created(new URI("/api/categorys/" + newCategory.getId()))
          .body(newCategory);
    }
  }

  @GetMapping("/categories")
  public ResponseEntity<List<Category>> getAll() {
    log.debug("REST request to retrieve all Categories");
    return ResponseEntity.ok().body(categoryRepository.findAll());
  }

  @GetMapping("/categories/{category_id}")
  public ResponseEntity<Category> getById(@PathVariable("category_id") Long categoryId) {
    log.debug("REST request to retrieve category #{}", categoryId);
    return categoryRepository.findById(categoryId)
        .map(response -> ResponseEntity.ok().body(response))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/categories/{category_id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("category_id") Long id) {
    log.debug("REST request to delete Category #{}", id);
    categoryService.delete(id);
    return ResponseEntity.ok().headers(HeaderUtil
        .createAlert("A category is deleted with identifier #" + id, id.toString()))
        .build();
  }
}
