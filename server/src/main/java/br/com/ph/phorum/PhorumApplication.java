package br.com.ph.phorum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {PhorumApplication.class})
@SpringBootApplication
public class PhorumApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhorumApplication.class, args);
  }
}
