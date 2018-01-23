package br.com.ph.phorum.infra.dto;

import br.com.ph.phorum.domain.entities.Answer;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

  private LocalDateTime createdIn;

  private UserDTO author;

  AnswerDTO(Answer answer) {
    this.content = answer.getContent();
    this.createdIn = answer.getCreatedIn();
    this.author = new UserDTO(answer.getAuthor());
  }
}

