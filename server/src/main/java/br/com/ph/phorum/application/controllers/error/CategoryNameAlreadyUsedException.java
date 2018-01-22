package br.com.ph.phorum.application.controllers.error;

public class CategoryNameAlreadyUsedException extends BadRequestAlertException {

  public CategoryNameAlreadyUsedException() {
    super(ErrorConstants.CATEGORY_NAME_ALREADY_USED_TYPE, "Category name already in use",
        "categoryManagement",
        "categoryexists");
  }
}
