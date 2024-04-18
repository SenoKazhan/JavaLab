package com.projects.countrycode.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.projects.countrycode.domain.City;
import com.projects.countrycode.domain.Country;
import com.projects.countrycode.repodao.CityRepository;
import com.projects.countrycode.service.CityService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ControllerCityTest {
  @Mock private CityService cityService;

  @Mock private CityRepository cityRepository;

  private ControllerCity cityController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    cityController = new ControllerCity(cityService, cityRepository);
  }

  @Test
  void testFindAllCities() {
    // Mock data
    List<City> cities = new ArrayList<>();
    cities.add(new City("City 1", new Country()));
    cities.add(new City("City 2", new Country()));

    // Mock service method
    when(cityService.findAllCities()).thenReturn(cities);

    // Call the controller method
    List<City> result = cityController.findAllCities();

    // Verify the service method was called
    verify(cityService).findAllCities();

    // Verify the result
    assertEquals(2, result.size());
    assertEquals("City 1", result.get(0).getName());
    assertEquals("City 2", result.get(1).getName());
  }

  @Test
  void deleteCity_shouldDeleteCityAndReturnSuccessMessage() {
    // Arrange
    Integer cityId = 1;

    // Mock behavior
    when(cityRepository.existsById(cityId)).thenReturn(true);

    // Act
    String result = cityController.deleteCity(cityId);

    // Assert
    verify(cityService).deleteCity(cityId);
    assertEquals("City deleted successfully", result);
  }

  @Test
  void deleteCity_shouldReturnErrorMessageIfCityNotFound() {
    // Arrange
    Integer cityId = 1;

    // Mock behavior
    when(cityRepository.existsById(cityId)).thenReturn(false);

    // Act
    String result = cityController.deleteCity(cityId);

    // Assert
    assertEquals("City not found", result);
  }

  @Test
  void testDeleteAllCommentsOfCountry() {
    // Mock data
    Integer tutorialId = 1;

    // Call the controller method
    ResponseEntity<List<City>> result = cityController.deleteAllCommentsOfCountry(tutorialId);

    // Verify the repository method was called
    verify(cityRepository).deleteByCountryId(tutorialId);

    // Verify the result
    assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    assertNull(result.getBody());
  }
}
