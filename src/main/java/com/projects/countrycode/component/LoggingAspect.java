package com.projects.countrycode.component;

import com.projects.countrycode.service.CounterService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** The type Logging aspect. */
@Aspect
@Component
public class LoggingAspect {
  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
  private final CounterService counterService;

  public LoggingAspect(CounterService counterService) {
    this.counterService = counterService;
  }

  /** Create. */
  @Pointcut("execution(* com.projects.countrycode.controller.*.create*(..))")
  public void create() {}

  /** Delete. */
  @Pointcut("execution(* com.projects.countrycode.controller.*.delete*(..))")
  public void delete() {}

  /** Update. */
  @Pointcut("execution(* com.projects.countrycode.controller.*.update*(..))")
  public void update() {}

  /** Find language by id. */
  @Pointcut("execution(* com.projects.countrycode.controller.*.findLanguageById(..))")
  public void findLanguageById() {}

  /** Find cou by lang. */
  @Pointcut("execution(* com.projects.countrycode.controller.*.findCouByLang(..))")
  public void findCouByLang() {}

  /** Find all countries. */
  @Pointcut("execution(* com.projects.countrycode.controller.*.findAllCountries(..))")
  public void findAllCountries() {}

  /** Add language to country. */
  @Pointcut("execution(* com.projects.countrycode.controller.*.addLanguageToCountry(..))")
  public void addLanguageToCountry() {}

  /**
   * Log create.
   *
   * @param result the result
   */
  @AfterReturning(pointcut = "create()", returning = "result")
  public void logCreate(Object result) {
    logger.info("Created: {}", result);
  }

  /**
   * Log delete.
   *
   * @param result the result
   */
  @AfterReturning(pointcut = "delete()", returning = "result")
  public void logDelete(Object result) {
    logger.info("Deleted: {}", result);
  }

  /**
   * Log update.
   *
   * @param result the result
   */
  @AfterReturning(pointcut = "update()", returning = "result")
  public void logUpdate(Object result) {
    logger.info("Updated: {}", result);
  }

  /**
   * Log find language by id.
   *
   * @param result the result
   */
  @AfterReturning(pointcut = "findLanguageById()", returning = "result")
  public void logFindLanguageById(Object result) {
    logger.info(
        "Language found by ID: {}, Request count: {}", result, counterService.getRequestCount());
  }

  /**
   * Log find cou by lang.
   *
   * @param result the result
   */
  @AfterReturning(pointcut = "findCouByLang()", returning = "result")
  public void logFindCouByLang(Object result) {
    logger.info(
        "Countries found by language ID: {}, Request count: {}",
        result,
        counterService.getRequestCount());
  }

  /**
   * Log find all countries.
   *
   * @param result the result
   */
  @AfterReturning(pointcut = "findAllCountries()", returning = "result")
  public void logFindAllCountries(Object result) {
    logger.info(
        "All countries found: {}, Request count: {}", result, counterService.getRequestCount());
  }

  /**
   * Log add language to country.
   *
   * @param result the result
   */
  @AfterReturning(pointcut = "addLanguageToCountry()", returning = "result")
  public void logAddLanguageToCountry(Object result) {
    logger.info("Language added to country: {}", result);
  }
}
