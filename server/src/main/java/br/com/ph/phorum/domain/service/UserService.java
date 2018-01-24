package br.com.ph.phorum.domain.service;

import br.com.ph.phorum.domain.entities.User;
import br.com.ph.phorum.domain.repository.UserRepository;
import br.com.ph.phorum.infra.dto.UserDTO;
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
}

