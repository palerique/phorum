package br.com.ph.phorum.domain.entities;

import br.com.ph.phorum.infra.dto.AnswerDTO;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Instant createdIn;

  @ManyToOne
  @JoinColumn(name = "topicId", nullable = false)
  private Topic topic;

  @ManyToOne
  @JoinColumn(name = "authorId", nullable = false)
  private User author;

  public Answer(Topic topic, User author, AnswerDTO answerDTO) {
    this.author = author;
    this.content = answerDTO.getContent();
    this.createdIn = Instant.now();
    this.topic = topic;
  }
}
