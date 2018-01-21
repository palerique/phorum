package br.com.ph.phorum.infra.vm;

import br.com.ph.phorum.infra.dto.UserDTO;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ManagedUserVM extends UserDTO {

  public static final int PASSWORD_MIN_LENGTH = 4;

  public static final int PASSWORD_MAX_LENGTH = 100;

  @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
  private String password;

}
