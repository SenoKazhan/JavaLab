package com.projects.countrycode.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.projects.countrycode.domain.Country;
import com.projects.countrycode.domain.Language;
import com.projects.countrycode.dto.CountryDto;
import com.projects.countrycode.repodao.CountryRepository;
import com.projects.countrycode.repodao.LanguageRepository;
import com.projects.countrycode.service.PhoneService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

class ControllerPhoneTest {

  @Mock private PhoneService phoneService;

  @Mock private CountryRepository countryRepository;

  @Mock private LanguageRepository languageRepository;

  @Mock private ControllerPhone phoneController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    countryRepository = mock(CountryRepository.class);
    languageRepository = mock(LanguageRepository.class);
    phoneController = new ControllerPhone(phoneService, countryRepository, languageRepository);
  }

  @Test
  void findAllCountries_shouldReturnListOfCountries() {
    // Arrange
    List<Country> countries = new ArrayList<>();
    countries.add(new Country());
    countries.add(new Country());
    when(phoneService.findAllCountries()).thenReturn(countries);

    // Act
    List<Country> result = phoneController.findAllCountries();

    // Assert
    assertEquals(countries.size(), result.size());
  }

  @Test
  void getCountryInfo_shouldReturnCountryInfoByCode() {
    // Arrange
    Long countryCode = 1L;
    List<Country> countries = new ArrayList<>();
    countries.add(new Country());
    when(phoneService.findByPhoneCode(countryCode)).thenReturn(countries);

    // Act
    List<Country> result = phoneController.getCountryInfo(countryCode);

    // Assert
    assertEquals(countries.size(), result.size());
  }

  @Test
  void getCountryInfo_shouldThrowNotFoundExceptionWhenNoCountryFound() {
    // Arrange
    Long phoneCode = 123456L;
    when(phoneService.findByPhoneCode(phoneCode)).thenReturn(Collections.emptyList());

    // Act and Assert
    assertThrows(
        ResponseStatusException.class,
        () -> {
          phoneController.getCountryInfo(phoneCode);
        });
  }

  @Test
  void findCountryById_shouldReturnCountryDto() {
    // Arrange
    Integer countryId = 1;
    Country country = new Country(countryId, "USA", "US", 1L);
    when(phoneService.findById(countryId)).thenReturn(country);

    // Act
    ResponseEntity<CountryDto> result = phoneController.findCountryById(countryId);

    // Assert
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(countryId, Objects.requireNonNull(result.getBody()).getId());
  }

  @Test
  void addLanguageToCountry_shouldReturnSuccessMessage() {
    // Arrange
    Integer countryId = 1;
    Country country = new Country();
    country.setId(countryId);
    Language language = new Language();
    language.setName("English");
    when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));
    when(languageRepository.findByName(language.getName())).thenReturn(Optional.empty());

    // Act
    ResponseEntity<String> result = phoneController.addLanguageToCountry(countryId, language);

    // Assert
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals("Новый язык успешно создан и добавлен к стране", result.getBody());
  }

  @Test
  void addLanguageToCountry_shouldAddExistingLanguageToCountry() {
    // Arrange
    Integer countryId = 1;
    Integer languageId = 1;
    Country country = new Country();
    Language language = new Language("English");
    language.setId(languageId);

    // Mock behavior of countryRepository.findById
    when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

    // Mock behavior of languageRepository.findByName
    when(languageRepository.findByName(language.getName())).thenReturn(Optional.of(language));

    // Act
    ResponseEntity<String> result = phoneController.addLanguageToCountry(countryId, language);

    // Assert
    assertEquals(HttpStatus.OK, result.getStatusCode());
    verify(countryRepository).save(country);
    assertEquals("Язык успешно добавлен к стране", result.getBody());
  }

  @Test
  void addLanguageToCountry_shouldReturnBadRequestWhenCountryNotFound() {
    // Arrange
    Integer countryId = 1;
    Language language = new Language("English");

    // Mock behavior of countryRepository.findById to return empty optional
    when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

    // Act
    ResponseEntity<String> result = phoneController.addLanguageToCountry(countryId, language);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }

  @Test
  void createCountry_shouldReturnSuccessMessage() {
    // Arrange
    Country country = new Country();

    // Act
    String result = phoneController.createCountry(country);

    // Assert
    assertEquals("Saved successfully", result);
  }

  @Test
  void updateCountry_shouldReturnSuccessMessage() {
    // Arrange
    Integer countryId = 1;
    Country country = new Country(countryId, "USA", "US", 1L);
    Country updatedCountry = new Country(countryId, "Updated USA", "US", 1L);
    when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

    // Act
    ResponseEntity<String> result = phoneController.updateCountry(countryId, updatedCountry);

    // Assert
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals("Updated successfully", result.getBody());
  }

  @Test
  void updateCountry_shouldReturnBadRequestWhenCountryNotFound() {
    // Arrange
    Integer countryId = 1;
    Country entityCountries = new Country();
    entityCountries.setName("USA");
    // Mock behavior of countryRepository.findById to return an empty optional
    when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

    // Act
    ResponseEntity<String> result = phoneController.updateCountry(countryId, entityCountries);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }
  @Test
  void updateCountry_shouldUpdateCodeWhenNotNull() {
    // Arrange
    Integer countryId = 1;
    String updatedName = "Updated Name";
    String updatedCode = "dsd";
    Country entityCountries = new Country();
    entityCountries.setName(updatedName);
    entityCountries.setCode(updatedCode);
    Country existingCountry = new Country();
    existingCountry.setId(countryId);
    when(countryRepository.findById(countryId)).thenReturn(Optional.of(existingCountry));

    // Act
    ResponseEntity<String> result = phoneController.updateCountry(countryId, entityCountries);

    // Assert
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(updatedName, existingCountry.getName());
    assertEquals(updatedCode, existingCountry.getCode());
  }

  @Test
  void updateCountry_shouldUpdateNameWhenNotNull() {
    // Arrange
    Integer countryId = 1;
    String updatedName = "Updated Name";
    Country entityCountries = new Country();
    entityCountries.setName(updatedName);
    Country existingCountry = new Country();
    existingCountry.setId(countryId);
    when(countryRepository.findById(countryId)).thenReturn(Optional.of(existingCountry));

    // Act
    ResponseEntity<String> result = phoneController.updateCountry(countryId, entityCountries);

    // Assert
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(updatedName, existingCountry.getName());
  }

  @Test
  void updateCountry_shouldUpdatePhoneCodeWhenNotNull() {
    // Arrange
    Integer countryId = 1;
    Long updatedPhoneCode = 123456L;
    Country entityCountries = new Country();
    entityCountries.setPhone(updatedPhoneCode);
    Country existingCountry = new Country();
    existingCountry.setId(countryId);
    when(countryRepository.findById(countryId)).thenReturn(Optional.of(existingCountry));

    // Act
    ResponseEntity<String> result = phoneController.updateCountry(countryId, entityCountries);

    // Assert
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(updatedPhoneCode, existingCountry.getPhone());
  }
  @Test
  void deleteCountry_shouldRemoveCountryFromAssociatedLanguages() {
    // Arrange
    Integer countryId = 1;
    Country country = new Country(countryId, "USA", "US", 1L);
    Language language1 = new Language("English");
    language1.addCountry(country);
    Language language2 = new Language("Spanish");
    language2.addCountry(country);
    Set<Language> languages = new HashSet<>();
    languages.add(language1);
    languages.add(language2);
    country.setLanguages(languages);

    when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

    // Act
    ResponseEntity<Country> result = phoneController.deleteCountry(countryId);

    // Assert
    assertEquals(HttpStatus.OK, result.getStatusCode());
    languages.forEach(language -> assertTrue(language.getCountries().isEmpty()));
  }

  @Test
  void deleteCountry_shouldReturnNotFoundWhenCountryNotFound() {
    // Arrange
    Integer countryId = 1;
    Country country = new Country(countryId, "USA", "US", 1L);
    when(languageRepository.findById(countryId)).thenReturn(Optional.empty());

    // Act
    ResponseEntity<Country> result = phoneController.deleteCountry(countryId);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }
}
