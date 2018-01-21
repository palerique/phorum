package br.com.ph.phorum.application.controllers.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
@Log4j2
@UtilityClass
public final class HeaderUtil {

  private static HttpHeaders createAlert(String message, String param) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-phorumApp-alert", message);
    headers.add("X-phorumApp-params", param);
    return headers;
  }

  public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
    return createAlert("A new " + entityName + " is created with identifier " + param, param);
  }

  public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
    return createAlert("A " + entityName + " is updated with identifier " + param, param);
  }

  public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
    return createAlert("A " + entityName + " is deleted with identifier " + param, param);
  }

  public static HttpHeaders createFailureAlert(String entityName, String errorKey,
    String defaultMessage) {
    log.error("Entity processing failed, {}", defaultMessage);
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-phorumApp-error", defaultMessage);
    headers.add("X-phorumApp-params", entityName);
    return headers;
  }
}
