package br.com.ph.phorum.infra.config;

import java.util.Arrays;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@Slf4j
public class Cors {

  @Bean
  public CorsFilter corsFilter() {
    log.debug("Registering CORS filter");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(Collections.singletonList("*"));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    config.setAllowedHeaders(Collections.singletonList("*"));
    config.setMaxAge(1800L);
    config.setAllowCredentials(true);
    source.registerCorsConfiguration("/api/**", config);
    source.registerCorsConfiguration("/v2/api-docs", config);
    return new CorsFilter(source);
  }
}
