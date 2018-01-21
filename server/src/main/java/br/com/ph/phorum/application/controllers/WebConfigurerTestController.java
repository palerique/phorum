package br.com.ph.phorum.application.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebConfigurerTestController {

  @GetMapping("/api/test-cors")
  public void testCorsOnApiPath() {
  }

  @GetMapping("/test/test-cors")
  public void testCorsOnOtherPath() {
  }
}
