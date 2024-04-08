package com.projects.countrycode.component;

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

  @Pointcut("execution(* com.projects.countrycode.controller.*.create*(..))")
  public void create() {}
// Аннотация @Pointcut используется для определения точек соединения в коде, где аспект должен быть применен
  @Pointcut("execution(* com.projects.countrycode.controller.*.delete*(..))")
  public void delete() {}

  @Pointcut("execution(* com.projects.countrycode.controller.*.update*(..))")
  public void update() {}

  @AfterReturning(pointcut = "create()", returning = "result")
  public void logCreate(Object result) {
    logger.info("Created: {}", result);
  }

  @AfterReturning(pointcut = "delete()", returning = "result")
  public void logDelete(Object result) {
    logger.info("Deleted: {}", result);
  }

  @AfterReturning(pointcut = "update()", returning = "result")
  public void logUpdate(Object result) {
    logger.info("Updated: {}", result);
  }
  //Эти аннотации используются для определения советов,
  // которые должны быть выполнены в определенные моменты жизненного цикла метода.
  // Например, @Before выполняется перед выполнением метода, @After после его выполнения, @Around оборачивает метод,
  // позволяя выполнить дополнительный код до и после вызова метода, @AfterReturning выполняется после успешного выполнения метода,
  // а @AfterThrowing после возникновения исключения в методе
}
