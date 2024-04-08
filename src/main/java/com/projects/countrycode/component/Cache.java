package com.projects.countrycode.component;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** The type Cache. */
@Component
@Data
public class Cache {
  private static final Logger logg = LoggerFactory.getLogger(Cache.class);
  private final Map<String, Object> hashMap =
      new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
          return size() > 15;
        }
      };

  /**
   * Put cache.
   *
   * @param key the key
   * @param value the value
   */
  public void putCache(String key, Object value) {
    hashMap.put(key, value);
    logg.info("Value has been put in cache");
  }

  /**
   * Gets cache.
   *
   * @param key the key
   * @return the cache
   */
  public Object getCache(String key) {
    logg.info("get from cache");
    return hashMap.get(key);
  }

  /**
   * Contains key boolean.
   *
   * @param key the key
   * @return the boolean
   */
  public boolean containsKey(String key) {
    return hashMap.containsKey(key);
  }

  /**
   * Remove.
   *
   * @param key the key
   */
  public void remove(String key) {
    hashMap.remove(key);
    logg.info("Removed from cache");
  }
}
