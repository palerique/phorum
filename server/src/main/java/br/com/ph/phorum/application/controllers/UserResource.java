package br.com.ph.phorum.application.controllers;

import br.com.ph.phorum.domain.User;
import br.com.ph.phorum.domain.repository.UserRepository;
import br.com.ph.phorum.domain.service.UserService;
import br.com.ph.phorum.infra.dto.UserDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

//    if (userDTO.getId() != null) {
//      throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement",
//        "idexists");
//      // Lowercase the user login before comparing with database
//    } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
//      throw new LoginAlreadyUsedException();
//    } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
//      throw new EmailAlreadyUsedException();
//    } else {
    User newUser = userService.createUser(userDTO);
    return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
      .body(newUser);
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> createUser() {
    log.debug("REST request to retrieve all Users");

//    if (userDTO.getId() != null) {
//      throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement",
//        "idexists");
//      // Lowercase the user login before comparing with database
//    } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
//      throw new LoginAlreadyUsedException();
//    } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
//      throw new EmailAlreadyUsedException();
//    } else {
    return ResponseEntity.ok()
      .body(Collections.singletonList(User.builder().name("CriadoComSucesso").build()));
  }
}


