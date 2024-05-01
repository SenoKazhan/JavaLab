package com.projects.countrycode.service;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;
import org.springframework.stereotype.Service;

/** The type Counter service. */
@Service
@Data
public class CounterService {
  private static final CounterService INSTANCE = new CounterService();

  private AtomicInteger requestCount;

  private CounterService() {
    requestCount = new AtomicInteger(0);
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static CounterService getInstance() {
    return INSTANCE;
  }

  /** Increment request count. */
  public synchronized void incrementRequestCount() {
    requestCount.incrementAndGet();
  }

  /**
   * Gets request count.
   *
   * @return the request count
   */
  public int getRequestCount() {
    return requestCount.get();
  }
}
