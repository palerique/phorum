package br.com.ph.phorum;

import static org.mockito.Mockito.validateMockitoUsage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseIntegrationTest {

  @Before
  public void abstractSetup() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void validate() {
    validateMockitoUsage();
  }
}
