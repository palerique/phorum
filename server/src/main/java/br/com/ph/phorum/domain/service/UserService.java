package br.com.ph.phorum.domain.service;

import br.com.ph.phorum.domain.User;
import br.com.ph.phorum.domain.repository.UserRepository;
import br.com.ph.phorum.infra.dto.UserDTO;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User registerUser(UserDTO userDTO, String password) {

    User newUser = new User();
    String encryptedPassword = passwordEncoder.encode(password);
    newUser.setLogin(userDTO.getLogin());
    // new user gets initially a generated password
    newUser.setPassword(encryptedPassword);
    newUser.setName(userDTO.getName());
    newUser.setEmail(userDTO.getEmail());
    // new user is not active
    // new user gets registration key
    userRepository.save(newUser);
    log.debug("Created Information for User: {}", newUser);
    return newUser;
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

  public Optional<UserDTO> updateUser(UserDTO userDTO) {
    //TODO
//    return Optional.of(userRepository
//      .findOne(userDTO.getId()))
//      .map(user -> {
//        user.setLogin(userDTO.getLogin());
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        user.setEmail(userDTO.getEmail());
//        user.setImageUrl(userDTO.getImageUrl());
//        user.setActivated(userDTO.isActivated());
//        user.setLangKey(userDTO.getLangKey());
//        Set<Authority> managedAuthorities = user.getAuthorities();
//        managedAuthorities.clear();
//        userDTO.getAuthorities().stream()
//          .map(authorityRepository::findOne)
//          .forEach(managedAuthorities::add);
//        cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).evict(user.getLogin());
//        cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).evict(user.getEmail());
//        log.debug("Changed Information for User: {}", user);
//        return user;
//      })
//      .map(UserDTO::new);
    log.error("Method to be implemented");
    return Optional.of(userDTO);
  }

  public void deleteUser(String login) {
    userRepository.findOneByLogin(login).ifPresent(user -> {
      userRepository.delete(user);
      log.debug("Deleted User: {}", user);
    });
  }
}

