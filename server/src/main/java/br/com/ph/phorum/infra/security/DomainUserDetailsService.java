package br.com.ph.phorum.infra.security;

import br.com.ph.phorum.domain.entities.User;
import br.com.ph.phorum.domain.repository.UserRepository;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public DomainUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String login) {
    log.debug("Authenticating {}", login);
    String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
    Optional<User> userByEmailFromDatabase = userRepository
        .findOneByEmail(lowercaseLogin);
    return userByEmailFromDatabase.map(this::createSpringSecurityUser)
        .orElseGet(() -> {
          Optional<User> userByLoginFromDatabase = userRepository
              .findOneByLogin(lowercaseLogin);
          return userByLoginFromDatabase.map(this::createSpringSecurityUser)
              .orElseThrow(() -> new UsernameNotFoundException(
                  "User " + lowercaseLogin + " was not found in the database"));
        });
  }

  private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
    return new org.springframework.security.core.userdetails.User(user.getLogin(),
        user.getPassword(),
        Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER)));
  }
}
