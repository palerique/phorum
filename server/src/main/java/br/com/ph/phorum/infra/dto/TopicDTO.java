package br.com.ph.phorum.infra.dto;

import br.com.ph.phorum.domain.entities.Answer;
import br.com.ph.phorum.domain.entities.Topic;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class TopicDTO {

  private Long id;

  @NotBlank
  @Size(min = 1, max = Topic.NAME_MAX_LENGTH)
  private String name;

  @NotBlank
  @Size(min = 1, max = Topic.CONTENT_MAX_LENGTH)
  private String content;

  private Instant createdIn;

  private UserDTO author;

  @NotNull
  private CategoryDTO category;

  private List<AnswerDTO> comments;

  public TopicDTO(Topic topic) {
    this.id = topic.getId();
    this.name = topic.getName();
    this.content = topic.getContent();
    this.createdIn = topic.getCreatedIn();
    this.author = new UserDTO(topic.getAuthor());
    this.category = new CategoryDTO(topic.getCategory());

    List<AnswerDTO> comments = new ArrayList<>();
    List<Answer> answers = topic.getAnswers();
    if (answers != null) {
      for (Answer answer : answers) {
        comments.add(new AnswerDTO(answer));
      }
    }
    this.comments = comments;
  }
}
