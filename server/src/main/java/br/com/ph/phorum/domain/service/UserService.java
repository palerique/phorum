package br.com.ph.phorum.domain.service;

import br.com.ph.phorum.application.controllers.error.BadRequestAlertException;
import br.com.ph.phorum.domain.entities.User;
import br.com.ph.phorum.domain.repository.UserRepository;
import br.com.ph.phorum.infra.dto.UserDTO;
import br.com.ph.phorum.infra.security.SecurityUtils;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User createUser(UserDTO userDTO) {
    User user = new User();
    user.setLogin(userDTO.getLogin());
    user.setName(userDTO.getName());
    user.setEmail(userDTO.getEmail());
    String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
    user.setPassword(encryptedPassword);
    userRepository.save(user);
    log.debug("Created Information for User: {}", user);
    return user;
  }

  public void delete(Long id) {
    userRepository.delete(userRepository.findOne(id));
    log.debug("Deleted User: {}", id);
  }

  public UserDTO getAuthenticated() {
    Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
    if (userLogin.isPresent()) {
      Optional<User> oneByLogin = userRepository.findOneByLogin(userLogin.get());
      if (oneByLogin.isPresent()) {
        return new UserDTO(oneByLogin.get());
      } else {
        throwException();
      }
    } else {
      throwException();
    }
    return null;
  }

  private UserDTO throwException() {
    throw new BadRequestAlertException("A new topic must have an author",
        "topicManagement", "usernotexists");
  }
}

