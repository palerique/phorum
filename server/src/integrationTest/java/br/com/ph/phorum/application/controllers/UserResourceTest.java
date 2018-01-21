package br.com.ph.phorum.application.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.ph.phorum.PhorumApplication;
import br.com.ph.phorum.TestUtil;
import br.com.ph.phorum.application.controllers.error.ExceptionTranslator;
import br.com.ph.phorum.domain.User;
import br.com.ph.phorum.domain.repository.UserRepository;
import br.com.ph.phorum.domain.service.UserService;
import br.com.ph.phorum.infra.dto.UserDTO;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PhorumApplication.class)
public class UserResourceTest {

  private static final String DEFAULT_LOGIN = "johndoe";

  private static final String DEFAULT_PASSWORD = "passjohndoe";

  private static final String DEFAULT_EMAIL = "johndoe@localhost";

  private static final String DEFAULT_FIRSTNAME = "john";

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private MappingJackson2HttpMessageConverter jacksonMessageConverter;

  @Autowired
  private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

  @Autowired
  private ExceptionTranslator exceptionTranslator;

  @Autowired
  private EntityManager em;

  private MockMvc restUserMockMvc;

  private User user;

  public static User createEntity(EntityManager em) {
    User user = new User();
    user.setLogin(DEFAULT_LOGIN + RandomStringUtils.randomAlphabetic(5));
    user.setPassword(RandomStringUtils.random(60));
    user.setEmail(RandomStringUtils.randomAlphabetic(5) + DEFAULT_EMAIL);
    return user;
  }

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    UserResource userResource = new UserResource(userRepository, userService);
    this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource)
      .setCustomArgumentResolvers(pageableArgumentResolver)
      .setControllerAdvice(exceptionTranslator)
      .setMessageConverters(jacksonMessageConverter)
      .build();
  }

  @Before
  public void initTest() {
    user = createEntity(em);
    user.setLogin(DEFAULT_LOGIN);
    user.setEmail(DEFAULT_EMAIL);
  }

  @Test
  @Transactional
  public void createUser() throws Exception {
    int databaseSizeBeforeCreate = userRepository.findAll().size();

    // Create the User
    UserDTO dto = UserDTO.builder()
      .login(DEFAULT_LOGIN)
      .password(DEFAULT_PASSWORD)
      .name(DEFAULT_FIRSTNAME)
      .email(DEFAULT_EMAIL)
      .build();

    restUserMockMvc.perform(post("/api/users")
      .contentType(TestUtil.APPLICATION_JSON_UTF8)
      .content(TestUtil.convertObjectToJsonBytes(dto)))
      .andExpect(status().isCreated());

    // Validate the User in the database
    List<User> userList = userRepository.findAll();
    assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
    User testUser = userList.get(userList.size() - 1);
    assertThat(testUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
    assertThat(testUser.getName()).isEqualTo(DEFAULT_FIRSTNAME);
    assertThat(testUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
  }


  @Test
  @Transactional
  public void createUserWithExistingId() throws Exception {
    int databaseSizeBeforeCreate = userRepository.findAll().size();

    // Create the User
    UserDTO dto = UserDTO.builder()
      .id(1L)
      .login(DEFAULT_LOGIN)
      .password(DEFAULT_PASSWORD)
      .name(DEFAULT_FIRSTNAME)
      .email(DEFAULT_EMAIL)
      .build();

    // An entity with an existing ID cannot be created, so this API call must fail
    restUserMockMvc.perform(post("/api/users")
      .contentType(TestUtil.APPLICATION_JSON_UTF8)
      .content(TestUtil.convertObjectToJsonBytes(dto)))
      .andExpect(status().isBadRequest());

    // Validate the User in the database
    List<User> userList = userRepository.findAll();
    assertThat(userList).hasSize(databaseSizeBeforeCreate);
  }
}
