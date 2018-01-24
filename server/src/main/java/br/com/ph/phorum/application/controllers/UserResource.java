package br.com.ph.phorum.application.controllers;

import br.com.ph.phorum.application.controllers.error.BadRequestAlertException;
import br.com.ph.phorum.application.controllers.error.EmailAlreadyUsedException;
import br.com.ph.phorum.application.controllers.error.LoginAlreadyUsedException;
import br.com.ph.phorum.application.controllers.util.HeaderUtil;
import br.com.ph.phorum.domain.entities.User;
import br.com.ph.phorum.domain.repository.UserRepository;
import br.com.ph.phorum.domain.service.UserService;
import br.com.ph.phorum.infra.dto.UserDTO;
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
public class UserResource {

  private final UserRepository userRepository;
  private final UserService userService;

  public UserResource(UserRepository userRepository,
      UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO)
      throws URISyntaxException {
    log.debug("REST request to save User : {}", userDTO);

    if (userDTO.getId() != null) {
      throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement",
          "idexists");
    } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
      throw new LoginAlreadyUsedException();
    } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
      throw new EmailAlreadyUsedException();
    } else {

      User newUser = userService.createUser(userDTO);
      return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
          .body(newUser);
    }
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAll() {
    log.debug("REST request to retrieve all Users");
    return ResponseEntity.ok().body(userRepository.findAll());
  }


  @GetMapping("/users/{user_id}")
  public ResponseEntity<User> getById(@PathVariable("user_id") Long userId) {
    log.debug("REST request to retrieve user #{}", userId);

    if (!userRepository.exists(userId)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return ResponseEntity.ok().body(userRepository.getOne(userId));
  }

  @DeleteMapping("/categories/{user_id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("user_id") Long id) {
    log.debug("REST request to delete User #{}", id);
    userService.delete(id);
    return ResponseEntity.ok().headers(HeaderUtil
        .createAlert("A user is deleted with identifier #" + id, id.toString()))
        .build();
  }
}


