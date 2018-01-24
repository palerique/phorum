package br.com.ph.phorum.infra.dto;

import br.com.ph.phorum.domain.entities.Answer;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {

  private Long id;

  @NotBlank
  private String content;

  private Instant createdIn;

  private UserDTO author;

  AnswerDTO(Answer answer) {
    this.content = answer.getContent();
    this.createdIn = answer.getCreatedIn();
    this.author = new UserDTO(answer.getAuthor());
  }
}

