package br.com.ph.phorum.domain.service;

import br.com.ph.phorum.domain.entities.Topic;
import br.com.ph.phorum.domain.entities.User;
import br.com.ph.phorum.domain.repository.CategoryRepository;
import br.com.ph.phorum.domain.repository.TopicRepository;
import br.com.ph.phorum.domain.repository.UserRepository;
import br.com.ph.phorum.infra.dto.TopicDTO;
import java.time.LocalDateTime;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
public class TopicService {

  private final TopicRepository topicRepository;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;

  public TopicService(TopicRepository topicRepository,
      UserRepository userRepository,
      CategoryRepository categoryRepository) {
    this.topicRepository = topicRepository;
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
  }

  public Topic createTopic(TopicDTO topicDTO) {
    return topicRepository.save(Topic.builder()
        .name(topicDTO.getName())
        .content(topicDTO.getContent())
        .createdIn(LocalDateTime.now())
        .author(getUser())
        .category(categoryRepository.getOne(topicDTO.getCategory().getId()))
        .build());
  }

  public User getUser() {
//    User user = SecurityUtils.getCurrentUserLogin()
//        .flatMap(userRepository::findOneByLogin)
//        .orElseThrow(() -> new BadRequestAlertException("A new topic must have an author",
//            "topicManagement", "usernotexists"));

    //TODO: remove when JWT auth is working!
    return userRepository.findAll().get(0);
  }
}

