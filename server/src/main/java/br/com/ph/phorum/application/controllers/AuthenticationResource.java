package br.com.ph.phorum.application.controllers;

import br.com.ph.phorum.infra.security.JWTConfigurer;
import br.com.ph.phorum.infra.security.TokenProvider;
import br.com.ph.phorum.infra.vm.LoginVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationResource {

  private final TokenProvider tokenProvider;

  private final AuthenticationManager authenticationManager;

  public AuthenticationResource(TokenProvider tokenProvider,
      AuthenticationManager authenticationManager) {
    this.tokenProvider = tokenProvider;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/authenticate")
  public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    boolean rememberMe = loginVM.getRememberMe() != null && loginVM.getRememberMe();
    String jwt = tokenProvider.createToken(authentication, rememberMe);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
    return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
  }

  /**
   * Object to return as body in JWT Authentication.
   */
  static class JWTToken {

    private String idToken;

    JWTToken(String idToken) {
      this.idToken = idToken;
    }

    @JsonProperty("id_token")
    String getIdToken() {
      return idToken;
    }

    void setIdToken(String idToken) {
      this.idToken = idToken;
    }
  }
}
