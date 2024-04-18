package com.projects.countrycode.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class LanguageTest {
  @Mock
  private Language language;

  @BeforeEach
  void setUp() {
    language = new Language();
  }

  @Test
  void testRemoveCountry_CountryExists() {
    // Mock data
    Integer countryId = 1;
    Country country = new Country();
    country.setId(countryId);

    Set<Country> countries = new HashSet<>();
    countries.add(country);

    language.setCountries(countries);

    // Call the method
    language.removeCountry(countryId);

    // Verify the country was removed from the language
    assertFalse(language.getCountries().contains(country));
    assertTrue(country.getLanguages().isEmpty());
  }

  @Test
  void testRemoveCountry_CountryNotExists() {
    // Mock data
    Integer countryId = 1;
    Country country = new Country();
    country.setId(2);

    Set<Country> countries = new HashSet<>();
    countries.add(country);

    language.setCountries(countries);

    // Call the method
    language.removeCountry(countryId);

    // Verify the country was not removed from the language
    assertTrue(language.getCountries().contains(country));
    assertTrue(country.getLanguages().isEmpty());
  }

  @Test
  void testLanguageConstructor() {
    // Prepare test data
    String languageName = "English";
    Set<Country> countries = new HashSet<>();
    countries.add(new Country());
    countries.add(new Country());

    // Create a Language object using the constructor
    Language language = new Language(languageName, countries);

    // Assert that the constructor correctly set the fields
    assertEquals(languageName, language.getName());
    assertEquals(countries, language.getCountries());
  }
}
