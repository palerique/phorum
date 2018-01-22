package br.com.ph.phorum.application.controllers;

import br.com.ph.phorum.application.controllers.error.BadRequestAlertException;
import br.com.ph.phorum.domain.entities.Topic;
import br.com.ph.phorum.domain.repository.TopicRepository;
import br.com.ph.phorum.domain.service.TopicService;
import br.com.ph.phorum.infra.dto.TopicDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    log.debug("REST request to save Topic : {}", topicDTO);

    if (topicDTO.getId() != null) {
      throw new BadRequestAlertException("A new topic cannot already have an ID", "topicManagement",
          "idexists");
    } else {
      Topic newTopic = topicService.createTopic(topicDTO);
      return ResponseEntity.created(new URI("/api/topics/" + newTopic.getId())).body(newTopic);
    }
  }

  @GetMapping("/topics")
  public ResponseEntity<List<Topic>> getAll() {
    log.debug("REST request to retrieve all topics");
    return ResponseEntity.ok().body(topicRepository.findAll());
  }

  @GetMapping("/topics/{topic_id}")
  public ResponseEntity<Topic> getById(@PathVariable("topic_id") Long topicId) {
    log.debug("REST request to retrieve topic #{}", topicId);
    return topicRepository.findById(topicId)
        .map(response -> ResponseEntity.ok().body(response))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}


