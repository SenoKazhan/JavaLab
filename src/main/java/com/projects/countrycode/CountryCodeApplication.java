package com.projects.countrycode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/** The type Country code application. */

@SpringBootApplication
@EnableAspectJAutoProxy
public class CountryCodeApplication {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(CountryCodeApplication.class, args);
  }

}
