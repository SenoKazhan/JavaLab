package com.projects.countrycode.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.projects.countrycode.domain.Country;
import com.projects.countrycode.domain.Language;
import com.projects.countrycode.repodao.CountryRepository;
import com.projects.countrycode.repodao.LanguageRepository;
import com.projects.countrycode.service.LanguageService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ControllerLanguageTest {

  @Mock private LanguageService languageService;

  @Mock private LanguageRepository languageRepository;

  @Mock private CountryRepository countryRepository;

  private ControllerLanguage languageController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    languageController =
        new ControllerLanguage(languageService, languageRepository, countryRepository);
  }

  @Test
  void findAllLanguages_shouldReturnListOfLanguages() {
    // Arrange
    List<Language> expectedLanguages = new ArrayList<>();
    expectedLanguages.add(new Language("English"));
    expectedLanguages.add(new Language("French"));
    when(languageService.getAllLanguages()).thenReturn(expectedLanguages);

    // Act
    List<Language> result = languageController.findAllLanguages();

    // Assert
    assertEquals(expectedLanguages, result);
  }

  @Test
  void findLanguageById_shouldReturnLanguage() {
    // Arrange
    Integer languageId = 1;
    Language expectedLanguage = new Language("English");
    expectedLanguage.setId(languageId);
    when(languageService.getLanguageById(languageId)).thenReturn(expectedLanguage);

    // Act
    Language result = languageController.findLanguageById(languageId);

    // Assert
    assertEquals(expectedLanguage, result);
  }

  @Test
  void findCouByLang_shouldReturnListOfCountries() {
    // Arrange
    Integer languageId = 1;
    List<Country> expectedCountries = new ArrayList<>();
    expectedCountries.add(new Country(1, "USA", "US", 1L));
    expectedCountries.add(new Country(2, "France", "FR", 33L));
    when(countryRepository.findCountriesByLanguageId(languageId)).thenReturn(expectedCountries);

    // Act
    List<Country> result = languageController.findCouByLang(languageId);

    // Assert
    assertEquals(expectedCountries, result);
  }

  @Test
  void createLanguage_shouldReturnSuccessMessage() {
    // Arrange
    Language language = new Language("Spanish");

    // Act
    String result = languageController.createLanguage(language);

    // Assert
    verify(languageService).save(language);
    assertEquals("Language saved successfully", result);
  }
  @Test
  void addCountryToLanguage_shouldReturnSuccessMessage() {
    // Arrange
    Integer languageId = 1;
    Language language = new Language("English");
    language.setId(languageId);
    Country country = new Country(1, "USA", "US", 1L);

    // Mock behavior of languageRepository.findById
    when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));
    when(countryRepository.findByName(country.getName())).thenReturn(Optional.empty());

    // Act
    ResponseEntity<String> result = languageController.addCountryToLanguage(languageId, country);

    // Assert
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals("Новая страна успешно создана и добавлена к языку", result.getBody());
  }


  @Test
  void updateLanguage_shouldReturnSuccessMessage() {
    // Arrange
    Integer languageId = 1;
    Language language = new Language("French");
    language.setId(languageId);

    // Act
    String result = languageController.updateLanguage(languageId, language);

    // Assert
    verify(languageService).update(language, languageId);
    assertEquals("Language updated successfully", result);
  }

  @Test
  void deleteLanguage_shouldDeleteLanguage() {
    // Arrange
    Integer languageId = 1;
    Language language = new Language("German");
    language.setId(languageId);
    Optional<Language> languageOptional = Optional.of(language);
    when(languageRepository.findById(languageId)).thenReturn(languageOptional);

    // Act
    ResponseEntity<Language> result = languageController.deleteLanguage(languageId);

    // Assert
    verify(languageService).deleteLanguage(languageId);
    assertEquals(HttpStatus.OK, result.getStatusCode());
  }
}
