package com.projects.countrycode.component;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CacheTest {

  private Cache cache;

  @BeforeEach
  public void setUp() {
    cache = new Cache();
  }

  @Test
  void testPutAndGetFromCache() {
    String key = "key";
    Object value = new Object();

    cache.putCache(key, value);
    Object retrievedValue = cache.getCache(key);

    assertNotNull(retrievedValue);
    assertEquals(value, retrievedValue);
  }

  @Test
  void testContainsKey() {
    String key = "key";
    Object value = new Object();
    cache.putCache(key, value);

    boolean contains = cache.containsKey(key);

    assertTrue(contains);
  }

  @Test
  void testRemoveFromCache() {

    String key = "key";
    Object value = new Object();
    cache.putCache(key, value);

    cache.remove(key);
    boolean contains = cache.containsKey(key);

    assertFalse(contains);
  }

  @Test
  void testHashMapSizeLimit() {
    // Add more than 15 entries to the cache
    for (int i = 1; i <= 20; i++) {
      cache.putCache("key" + i, new Object());
    }

    // Assert that the size of the hashMap is 15
    assertEquals(15, cache.getHashMap().size());

    // Assert that the eldest entry (the first one added) has been removed
    assertFalse(cache.containsKey("key1"));

    // Assert that the most recent entries are still in the cache
    assertTrue(cache.containsKey("key16"));
  }

  @Test
  void testEquals() {
    Cache cache1 = new Cache();
    cache1.putCache("key1", "value1");
    Cache cache2 = new Cache();
    cache2.putCache("key1", "value1");

    // Assuming EqualsVerifier is set up correctly
    assertEquals(cache1, cache2);
  }

  @Test
  void testToString() {
    Cache cache = new Cache();
    // Assuming the toString method is implemented to return a string representation of the hashMap
    assertTrue(cache.toString().contains("hashMap"));
  }

  @Test
  void testHashCode() {
    Cache cache1 = new Cache();
    cache1.putCache("key1", "value1");
    Cache cache2 = new Cache();
    cache2.putCache("key1", "value1");

    // Assuming EqualsVerifier is set up correctly
    assertEquals(cache1.hashCode(), cache2.hashCode());
  }
}
