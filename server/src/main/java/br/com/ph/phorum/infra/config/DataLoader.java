package br.com.ph.phorum.infra.config;

import br.com.ph.phorum.domain.entities.Category;
import br.com.ph.phorum.domain.repository.CategoryRepository;
import br.com.ph.phorum.domain.service.UserService;
import br.com.ph.phorum.infra.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

  private UserService userService;
  private CategoryRepository categoryRepository;

  @Autowired
  public DataLoader(UserService userService,
      CategoryRepository categoryRepository) {
    this.userService = userService;
    this.categoryRepository = categoryRepository;
  }

  public void run(ApplicationArguments args) {
    userService.createUser(UserDTO.builder()
        .login("ph")
        .password("123456")
        .email("palerique@gmail.com")
        .name("Paulo Henrique")
        .build());

    categoryRepository.save(Category.builder().name("Development").build());
    categoryRepository.save(Category.builder().name("General").build());
    categoryRepository.save(Category.builder().name("Randon").build());
  }
}
