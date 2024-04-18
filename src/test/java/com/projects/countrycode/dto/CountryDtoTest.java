package com.projects.countrycode.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountryDtoTest {

  private CountryDto countryDto;

  @BeforeEach
  public void setUp() {
    countryDto = new CountryDto();
    countryDto.setId(1);
    countryDto.setName("United States");
    countryDto.setCode("US");
    countryDto.setPhone(1234567890L);
    countryDto.setLanguages(Arrays.asList("English", "Spanish"));
  }

  @Test
  void testGetId() {
    assertEquals(1, countryDto.getId());
  }

  @Test
  void testGetName() {
    assertEquals("United States", countryDto.getName());
  }

  @Test
  void testGetCode() {
    assertEquals("US", countryDto.getCode());
  }

  @Test
  void testGetPhone() {
    assertEquals(1234567890L, countryDto.getPhone());
  }

  @Test
  void testGetLanguages() {
    List<String> expectedLanguages = Arrays.asList("English", "Spanish");
    assertEquals(expectedLanguages, countryDto.getLanguages());
  }

  @Test
   void testEquals() {
    CountryDto country1 = new CountryDto();
    country1.setId(1);
    country1.setName("United States");
    country1.setCode("US");
    country1.setPhone(1234567890L);
    country1.setLanguages(Arrays.asList("English", "Spanish"));

    CountryDto country2 = new CountryDto();
    country2.setId(1);
    country2.setName("United States");
    country2.setCode("US");
    country2.setPhone(1234567890L);
    country2.setLanguages(Arrays.asList("English", "Spanish"));

    CountryDto country3 = new CountryDto();
    country3.setId(2);
    country3.setName("Canada");
    country3.setCode("CA");
    country3.setPhone(1234567891L);
    country3.setLanguages(Arrays.asList("English", "French"));

    // Test equality
    assertEquals(country1, country2);
    assertNotEquals(country1, country3);
  }

  @Test
  void testHashCode() {
    CountryDto country1 = new CountryDto();
    country1.setId(1);
    country1.setName("United States");
    country1.setCode("US");
    country1.setPhone(1234567890L);
    country1.setLanguages(Arrays.asList("English", "Spanish"));

    CountryDto country2 = new CountryDto();
    country2.setId(1);
    country2.setName("United States");
    country2.setCode("US");
    country2.setPhone(1234567890L);
    country2.setLanguages(Arrays.asList("English", "Spanish"));

    // Test hashCode consistency
    assertEquals(country1.hashCode(), country2.hashCode());
  }
}
