package br.com.ph.phorum.infra.config;

import br.com.ph.phorum.infra.security.JWTConfigurer;
import br.com.ph.phorum.infra.security.TokenProvider;
import javax.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@SuppressWarnings({"PMD.AvoidCatchingGenericException", "PMD.SignatureDeclareThrowsException"})
@Configuration
@Import(SecurityProblemSupport.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Log4j2
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final UserDetailsService userDetailsService;

  private final TokenProvider tokenProvider;

  private final CorsFilter corsFilter;

  private final SecurityProblemSupport problemSupport;

  public WebSecurity(AuthenticationManagerBuilder authenticationManagerBuilder,
      UserDetailsService userDetailsService, TokenProvider tokenProvider, CorsFilter corsFilter,
      SecurityProblemSupport problemSupport) {
    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.userDetailsService = userDetailsService;
    this.tokenProvider = tokenProvider;
    this.corsFilter = corsFilter;
    this.problemSupport = problemSupport;
  }

  @PostConstruct
  public void init() {
    try {
      authenticationManagerBuilder
          .userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder());
    } catch (Exception e) {
      throw new BeanInitializationException("Security configuration failed", e);
    }
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(
      org.springframework.security.config.annotation.web.builders.WebSecurity web) {
    web.ignoring()
        .antMatchers(HttpMethod.OPTIONS, "/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling()
        .authenticationEntryPoint(problemSupport)
        .accessDeniedHandler(problemSupport)
        .and()
        .csrf()
        .disable()
        .headers()
        .frameOptions()
        .disable()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/api/authenticate").permitAll()
        .antMatchers("/api/**").authenticated()
        .and()
        .apply(securityConfigurerAdapter());

  }

  private JWTConfigurer securityConfigurerAdapter() {
    return new JWTConfigurer(tokenProvider);
  }
}
