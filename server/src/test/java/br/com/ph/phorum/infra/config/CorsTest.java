package br.com.ph.phorum.infra.config;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.ph.phorum.application.controllers.WebConfigurerTestController;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CorsFilter;

public class CorsTest {

  public static final String OTHER_DOMAIN_COM = "other.domain.com";
  public static final String API_TEST_CORS = "/api/test-cors";
  public static final String POST = "POST";
  private final CorsFilter corsFilter = new Cors().corsFilter();

  @Before
  public void setup() {
    MockServletContext servletContext = spy(new MockServletContext());
    doReturn(new MockFilterRegistration())
      .when(servletContext).addFilter(anyString(), any(Filter.class));
    doReturn(new MockServletRegistration())
      .when(servletContext).addServlet(anyString(), any(Servlet.class));
  }

  @Test
  public void testCorsFilterOnApiPath() throws Exception {

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerTestController())
      .addFilters(corsFilter)
      .build();

    mockMvc.perform(
      options(API_TEST_CORS)
        .header(HttpHeaders.ORIGIN, OTHER_DOMAIN_COM)
        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, POST))
      .andExpect(status().isOk())
      .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, OTHER_DOMAIN_COM))
      .andExpect(header().string(HttpHeaders.VARY, "Origin"))
      .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE"))
      .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true"))
      .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1800"));

    mockMvc.perform(
      get(API_TEST_CORS)
        .header(HttpHeaders.ORIGIN, OTHER_DOMAIN_COM))
      .andExpect(status().isOk())
      .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, OTHER_DOMAIN_COM));
  }

  @Test
  public void testCorsFilterOnOtherPath() throws Exception {
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WebConfigurerTestController())
      .addFilters(corsFilter)
      .build();

    mockMvc.perform(
      get("/test/test-cors")
        .header(HttpHeaders.ORIGIN, OTHER_DOMAIN_COM))
      .andExpect(status().isOk())
      .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
  }

  static class MockFilterRegistration implements FilterRegistration, FilterRegistration.Dynamic {

    @Override
    public void addMappingForServletNames(EnumSet<DispatcherType> dispatcherTypes,
      boolean isMatchAfter, String... servletNames) {
      //to mock
    }

    @Override
    public Collection<String> getServletNameMappings() {
      return null;
    }

    @Override
    public void addMappingForUrlPatterns(EnumSet<DispatcherType> dispatcherTypes,
      boolean isMatchAfter, String... urlPatterns) {
      //to mock
    }

    @Override
    public Collection<String> getUrlPatternMappings() {
      return null;
    }

    @Override
    public void setAsyncSupported(boolean isAsyncSupported) {
      //to mock
    }

    @Override
    public String getName() {
      return null;
    }

    @Override
    public String getClassName() {
      return null;
    }

    @Override
    public boolean setInitParameter(String name, String value) {
      return false;
    }

    @Override
    public String getInitParameter(String name) {
      return null;
    }

    @Override
    public Set<String> setInitParameters(Map<String, String> initParameters) {
      return null;
    }

    @Override
    public Map<String, String> getInitParameters() {
      return null;
    }
  }

  static class MockServletRegistration implements ServletRegistration, ServletRegistration.Dynamic {

    @Override
    public void setLoadOnStartup(int loadOnStartup) {
      //to mock
    }

    @Override
    public Set<String> setServletSecurity(ServletSecurityElement constraint) {
      return null;
    }

    @Override
    public void setMultipartConfig(MultipartConfigElement multipartConfig) {
      //to mock
    }

    @Override
    public void setAsyncSupported(boolean isAsyncSupported) {
      //to mock
    }

    @Override
    public Set<String> addMapping(String... urlPatterns) {
      return null;
    }

    @Override
    public Collection<String> getMappings() {
      return null;
    }

    @Override
    public String getRunAsRole() {
      return null;
    }

    @Override
    public void setRunAsRole(String roleName) {
      //to mock
    }

    @Override
    public String getName() {
      return null;
    }

    @Override
    public String getClassName() {
      return null;
    }

    @Override
    public boolean setInitParameter(String name, String value) {
      return false;
    }

    @Override
    public String getInitParameter(String name) {
      return null;
    }

    @Override
    public Set<String> setInitParameters(Map<String, String> initParameters) {
      return null;
    }

    @Override
    public Map<String, String> getInitParameters() {
      return null;
    }
  }
}
