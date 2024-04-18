package com.projects.countrycode.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.projects.countrycode.component.Cache;
import com.projects.countrycode.domain.City;
import com.projects.countrycode.domain.Country;
import com.projects.countrycode.repodao.CityRepository;
import com.projects.countrycode.repodao.CountryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;

class CityServiceTest {

  @Mock private CityRepository cityRepository;
  @Mock private CountryRepository countryRepository;
  @Mock private Cache cache;
  @Mock private CityService cityService;

  @BeforeEach
  void setUp() {
    cityRepository = mock(CityRepository.class);
    countryRepository = mock(CountryRepository.class);
    cache = mock(Cache.class);
    cityService = new CityServiceImp(cityRepository, countryRepository, cache);
  }

  @Test
  void save_ShouldSaveCity_WhenCountryExists() {
    // Arrange
    Integer countryId = 1;
    City cityRequest = new City();
    Country country = new Country();
    when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

    // Act
    cityService.save(cityRequest, countryId);

    // Assert
    verify(cityRepository, times(1)).save(cityRequest);
    verify(cache, times(1)).putCache(anyString(), any());
  }

  @Test
  void save_ShouldThrowConflictException_WhenCountryNotFound() {
    // Arrange
    Integer countryId = 1;
    City cityRequest = new City();
    when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(ResponseStatusException.class, () -> cityService.save(cityRequest, countryId));
    verify(cityRepository, never()).save(cityRequest);
    verify(cache, never()).putCache(anyString(), any());
  }

  @Test
  void saveBulk_ShouldThrowResponseStatusException_WhenCountryNotFound() {
    // Arrange
    Integer countryId = 1;
    List<City> cityList = new ArrayList<>();
    City city1 = new City();
    City city2 = new City();
    cityList.add(city1);
    cityList.add(city2);

    City cityRequest = new City(); // Create an instance of City

    when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

    // Act & Assert
    ResponseStatusException exception =
        assertThrows(
            ResponseStatusException.class, () -> cityService.saveBulk(cityList, countryId));

    assertThrows(ResponseStatusException.class, () -> cityService.save(cityRequest, countryId));
  }

  @Test
  void findAllCities_ShouldReturnListOfCities() {
    // Arrange
    List<City> cityList = new ArrayList<>();
    cityList.add(new City());
    cityList.add(new City());
    when(cityRepository.findAll()).thenReturn(cityList);

    // Act
    List<City> result = cityService.findAllCities();

    // Assert
    assertEquals(cityList, result);
  }

  @Test
  void getCityById_ShouldReturnCity_WhenCityFoundInCache() {
    // Arrange
    Integer cityId = 1;
    City city = new City();
    when(cache.containsKey("city-" + cityId)).thenReturn(true);
    when(cache.getCache("city-" + cityId)).thenReturn(city);

    // Act
    City result = cityService.getCityById(cityId);

    // Assert
    assertEquals(city, result);
  }

  @Test
  void getCityById_ShouldReturnCity_WhenCityFoundInRepository() {
    // Arrange
    Integer cityId = 1;
    City city = new City();
    when(cache.containsKey("city-" + cityId)).thenReturn(false);
    when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

    // Act
    City result = cityService.getCityById(cityId);

    // Assert
    assertEquals(city, result);
  }

  @Test
  void getCityById_ShouldThrowNotFoundStatusException_WhenCityNotFound() {
    // Arrange
    Integer cityId = 1;
    when(cache.containsKey("city-" + cityId)).thenReturn(false);
    when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ResponseStatusException.class, () -> cityService.getCityById(cityId));
  }

  @Test
  void updateCity_ShouldUpdateCity() {
    // Arrange
    Integer cityId = 1;
    City cityRequest = new City();
    City city = new City();
    when(cache.containsKey("city-" + cityId)).thenReturn(true);
    when(cache.getCache("city-" + cityId)).thenReturn(city);

    // Act
    cityService.updateCity(cityRequest, cityId);

    // Assert
    verify(cityRepository).save(city);
    verify(cache).putCache("city-" + cityId, city);
  }

  @Test
  void deleteCity_ShouldDeleteCity() {
    // Arrange
    Integer cityId = 1;

    // Act
    cityService.deleteCity(cityId);

    // Assert
    verify(cityRepository).deleteById(cityId);
    verify(cache).remove("city-" + cityId);
  }
}
