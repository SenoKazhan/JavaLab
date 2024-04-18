package com.projects.countrycode.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class CountryTest {
  @Mock private Country country;

  @BeforeEach
  void setUp() {
    country = new Country();
  }

  @Test
  void testAddCity() {
    Country country = new Country();
    City city = new City();
    country.addCity(city);
    assertTrue(country.getCities().contains(city));
    assertEquals(country, city.getCountry());
  }

  @Test
  void testSetCities() throws Exception {
    Country country = new Country();
    List<City> cities = Arrays.asList(new City(), new City());
    country.setCities(cities);

    Field field = Country.class.getDeclaredField("cities");
    field.setAccessible(true);
    List<City> actualCities = (List<City>) field.get(country);

    assertEquals(cities, actualCities);
  }
}
