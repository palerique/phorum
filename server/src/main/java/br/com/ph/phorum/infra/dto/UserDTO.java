package br.com.ph.phorum.infra.dto;

import br.com.ph.phorum.domain.entities.User;
import br.com.ph.phorum.infra.security.Constants;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDTO {

  private Long id;

  @NotBlank
  @Pattern(regexp = Constants.LOGIN_REGEX)
  @Size(min = 1, max = 50)
  private String login;

  @Size(max = 50)
  private String name;

  @Size(max = 50)
  private String password;

  @Email
  @Size(min = 5, max = 100)
  private String email;

  private Set<String> authorities;

  UserDTO(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.name = user.getName();
  }
}
