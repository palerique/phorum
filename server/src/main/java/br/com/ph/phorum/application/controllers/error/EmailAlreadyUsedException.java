package br.com.ph.phorum.application.controllers.error;

public class EmailAlreadyUsedException extends BadRequestAlertException {

  public EmailAlreadyUsedException() {
    super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Email address already in use", "userManagement",
        "emailexists");
  }
}
