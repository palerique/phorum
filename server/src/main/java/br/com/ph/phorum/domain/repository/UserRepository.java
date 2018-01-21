package br.com.ph.phorum.domain.repository;

import br.com.ph.phorum.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findOneByLogin(String login);

  Optional<User> findOneByEmail(String lowercaseLogin);
}
