package br.com.ph.phorum.domain.service;

import br.com.ph.phorum.domain.entities.Answer;
import br.com.ph.phorum.domain.entities.Topic;
import br.com.ph.phorum.domain.entities.User;
import br.com.ph.phorum.domain.repository.AnswerRepository;
import br.com.ph.phorum.domain.repository.CategoryRepository;
import br.com.ph.phorum.domain.repository.TopicRepository;
import br.com.ph.phorum.domain.repository.UserRepository;
import br.com.ph.phorum.infra.dto.AnswerDTO;
import br.com.ph.phorum.infra.dto.TopicDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
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
  private final AnswerRepository answerRepository;

  public TopicService(TopicRepository topicRepository,
      UserRepository userRepository,
      CategoryRepository categoryRepository,
      AnswerRepository answerRepository) {
    this.topicRepository = topicRepository;
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
    this.answerRepository = answerRepository;
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

  public void delete(Long id) {
    Topic topic = topicRepository.getOne(id);
    answerRepository.deleteInBatch(topic.getAnswers());
    topicRepository.delete(topic);
    log.debug("Deleted Topic: {}", topic);
  }

  public Optional<Topic> updateTopic(@Valid TopicDTO topicDTO) {
    return Optional.of(getTopic(topicDTO, topicRepository.getOne(topicDTO.getId())));
  }

  private Topic getTopic(@Valid TopicDTO topicDTO, Topic topic) {
    topic.setName(topicDTO.getName());
    topic.setContent(topicDTO.getContent());

    topic.setCategory(categoryRepository.findOne(topicDTO.getCategory().getId()));
    log.debug("Changed Information for Topic: {}", topic);
    return topic;
  }

  public Optional<TopicDTO> addComment(Long topicId, AnswerDTO answerDTO) {
    Topic topic = topicRepository.findOne(topicId);
    Answer answer = new Answer(topic, getUser(), answerDTO);
    answerRepository.save(answer);
    topic.addAnswer(answer);
    return Optional.of(new TopicDTO(topic));
  }

  public List<TopicDTO> getAll() {
    List<TopicDTO> result = new ArrayList<>();
    for (Topic topic : topicRepository.findAll()) {
      result.add(new TopicDTO(topic));
    }
    return result;
  }
}

