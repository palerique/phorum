package br.com.ph.phorum;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class BaseApplicationIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private WebApplicationContext wac;
  private MockMvc mvc;

  @Before
  public void abstractSetup() {
    this.mvc = MockMvcBuilders.webAppContextSetup(wac).defaultRequest(get("/")).build();
  }

  @Test
  public void shouldShowHealthInfo() throws Exception {
    this.mvc.perform(get("/actuator/health").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.status").value("UP"));
  }
}
