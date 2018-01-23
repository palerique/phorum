package br.com.ph.phorum.infra.dto;

import br.com.ph.phorum.domain.entities.Category;
import javax.validation.constraints.NotBlank;
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
public class CategoryDTO {

  private Long id;

  @NotBlank
  @Size(min = 1, max = 50)
  private String name;

  CategoryDTO(Category category) {
    this.id = category.getId();
    this.name = category.getName();
  }
}
