package br.com.ph.phorum.application.controllers;

import br.com.ph.phorum.application.controllers.error.BadRequestAlertException;
import br.com.ph.phorum.application.controllers.util.HeaderUtil;
import br.com.ph.phorum.domain.entities.Topic;
import br.com.ph.phorum.domain.repository.TopicRepository;
import br.com.ph.phorum.domain.service.TopicService;
import br.com.ph.phorum.infra.dto.AnswerDTO;
import br.com.ph.phorum.infra.dto.TopicDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class TopicResource {

  private final TopicRepository topicRepository;
  private final TopicService topicService;

  public TopicResource(TopicRepository topicRepository, TopicService topicService) {
    this.topicRepository = topicRepository;
    this.topicService = topicService;
  }

  @PostMapping("/topics")
  public ResponseEntity<Topic> createTopic(@Valid @RequestBody TopicDTO topicDTO)
      throws URISyntaxException {
    log.debug("REST request to save Topic: {}", topicDTO);

    if (topicDTO.getId() != null) {
      throw new BadRequestAlertException("A new topic cannot already have an ID", "topicManagement",
          "idexists");
    } else {
      Topic newTopic = topicService.createTopic(topicDTO);
      return ResponseEntity.created(new URI("/api/topics/" + newTopic.getId())).body(newTopic);
    }
  }

  @PutMapping("/topics")
  public ResponseEntity<Topic> updateTopic(@Valid @RequestBody TopicDTO topicDTO) {
    log.debug("REST request to update Topic: {}", topicDTO);

    if (topicDTO.getId() == null) {
      throw new BadRequestAlertException("A new topic should already have an ID", "topicManagement",
          "idinexists");
    } else {
      return topicService.updateTopic(topicDTO)
          .map(response -> ResponseEntity.ok().body(response))
          .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
  }

  @GetMapping("/topics")
  public ResponseEntity<List<TopicDTO>> getAll() {
    log.debug("REST request to retrieve all topics");
    List<TopicDTO> topicDTOS = topicService.getAll();
    return ResponseEntity.ok().body(topicDTOS);
  }

  @GetMapping("/topics/{topic_id}")
  public ResponseEntity<Topic> getById(@PathVariable("topic_id") Long topicId) {
    log.debug("REST request to retrieve topic #{}", topicId);

    if (!topicRepository.exists(topicId)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return ResponseEntity.ok().body(topicRepository.getOne(topicId));
  }

  @DeleteMapping("/topics/{topic_id}")
  public ResponseEntity<Void> deleteTopic(@PathVariable("topic_id") Long id) {
    log.debug("REST request to delete Topic #{}", id);
    topicService.delete(id);
    return ResponseEntity.ok().headers(HeaderUtil
        .createAlert("A topic is deleted with identifier #" + id, id.toString()))
        .build();
  }

  @PostMapping("/topics/{topic_id}/comments")
  public ResponseEntity<TopicDTO> createTopic(
      @PathVariable("topic_id") Long topicId,
      @Valid @RequestBody AnswerDTO answerDTO
  ) {
    log.debug("REST request to add comment: {} to Topic #{}", answerDTO, topicId);
    return topicService.addComment(topicId, answerDTO)
        .map(response -> ResponseEntity.ok().body(response))
        .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
  }
}


