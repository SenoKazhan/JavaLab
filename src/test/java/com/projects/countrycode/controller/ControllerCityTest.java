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
  void findCityById_shouldReturnCity() {
    // Arrange
    Integer cityId = 1;
    City expectedCity = new City("Test City", new Country());
    when(cityService.getCityById(cityId)).thenReturn(expectedCity);

    // Act
    City result = cityController.findCityById(cityId);

    // Assert
    assertEquals(expectedCity, result);
  }
  @Test
  void createCity_shouldReturnSuccessMessage() {
    // Arrange
    Integer countryId = 1;
    City cityRequest = new City("New City", new Country());

    // Act
    String result = cityController.createCity(countryId, cityRequest);

    // Assert
    verify(cityService).save(cityRequest, countryId);
    assertEquals("New city was added", result);
  }

  @Test
  void createCities_shouldReturnSuccessMessage() {
    // Arrange
    Integer countryId = 1;
    List<City> cityList = new ArrayList<>();
    cityList.add(new City("City 1", new Country()));
    cityList.add(new City("City 2", new Country()));

    // Act
    String result = cityController.createCities(countryId, cityList);

    // Assert
    verify(cityService).saveBulk(cityList, countryId);
    assertEquals("New cities were added", result);
  }

  @Test
  void updateCity_shouldUpdateCity() {
    // Arrange
    Integer cityId = 1;
    Integer countryId = 1;
    City cityRequest = new City("Updated City", new Country());

    // Act
    cityController.updateCity(cityId, countryId, cityRequest);

    // Assert
    verify(cityService).updateCity(cityRequest, cityId);
  }
  @Test
  void getCitiesByCountry_shouldReturnListOfCities() {
    // Arrange
    Integer countryId = 1;
    List<City> expectedCities = new ArrayList<>();
    expectedCities.add(new City("City 1", new Country()));
    expectedCities.add(new City("City 2", new Country()));
    when(cityRepository.findCitiesByCountryId(countryId)).thenReturn(expectedCities);

    // Act
    List<City> result = cityController.getCitiesByCountry(countryId);

    // Assert
    assertEquals(expectedCities, result);
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

    // Act
    String result = cityController.deleteCity(cityId);

    // Assert
    verify(cityService).deleteCity(cityId);
    assertEquals("City deleted successfully", result);
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
