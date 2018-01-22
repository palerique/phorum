package br.com.ph.phorum.domain.entities;

import java.time.LocalDateTime;
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
public class Topic {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private LocalDateTime createdIn;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  private User author;

  @ManyToOne
  @JoinColumn(name = "categoryId", nullable = false)
  private Category category;
}