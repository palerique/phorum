package br.com.ph.phorum.application.controllers.error;

public class LoginAlreadyUsedException extends BadRequestAlertException {

  public LoginAlreadyUsedException() {
    super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Login already in use", "userManagement",
      "userexists");
  }
}
