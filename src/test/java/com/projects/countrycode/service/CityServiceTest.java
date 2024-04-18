package com.projects.countrycode.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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
  private static final String CACHE_KEY = "city-";

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
  void testSaveBulk_CountryExists() {
    // Mock data
    int countryId = 1;
    List<City> cityList = new ArrayList<>();
    Country country = new Country();
    when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

    // Call the service method
    cityService.saveBulk(cityList, countryId);

    // Verify the repository method was called for each city
    verify(cityRepository, times(cityList.size())).save(any());

    // Verify the cache was updated for each city
    verify(cache, times(cityList.size())).putCache(anyString(), any());

    // Verify the cities were associated with the country
    for (City city : cityList) {
      assertSame(country, city.getCountry());
    }
  }

  @Test
  void testSaveBulk_CountryNotFound() {
    // Mock data
    int countryId = 1;
    List<City> cityList = new ArrayList<>();
    when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

    // Call the service method and verify the exception
    assertThrows(ResponseStatusException.class, () -> cityService.saveBulk(cityList, countryId));
    // Verify the repository method was not called
    verify(cityRepository, never()).save(any());

    // Verify the cache was not updated
    verify(cache, never()).putCache(anyString(), any());
  }

  @Test
  void testSaveBulk_AssociateCitiesWithCountry() {
    // Mock data
    int countryId = 1;
    Country country = new Country();
    when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

    List<City> cityList = new ArrayList<>();
    City city1 = new City();
    City city2 = new City();
    cityList.add(city1);
    cityList.add(city2);

    // Call the service method
    cityService.saveBulk(cityList, countryId);

    // Verify the cities were associated with the country
    for (City city : cityList) {
      assertSame(country, city.getCountry());
    }
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
  void testUpdateCity_CityExists() {
    // Mock data
    int cityId = 1;
    City city = new City();
    city.setName("Old City Name");
    City updatedCityRequest = new City();
    updatedCityRequest.setName("New City Name");
    when(cache.containsKey(CACHE_KEY + cityId)).thenReturn(false);
    when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

    // Call the service method
    cityService.updateCity(updatedCityRequest, cityId);

    // Verify the repository method was called with the updated city
    verify(cityRepository, times(1)).save(city);

    // Verify the cache was updated with the updated city
    verify(cache, times(1)).putCache(CACHE_KEY + cityId, city);

    // Verify the city name was updated
    assertEquals(updatedCityRequest.getName(), city.getName());
  }

  @Test
  void testUpdateCity_CityNotFound() {
    // Mock data
    int cityId = 1;
    City updatedCityRequest = new City();
    updatedCityRequest.setName("New City Name");
    when(cache.containsKey(CACHE_KEY + cityId)).thenReturn(false);
    when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

    // Call the service method and verify the exception

    assertThrows(ResponseStatusException.class, () -> cityService.updateCity(updatedCityRequest, cityId));

    // Verify the repository method was not called
    verify(cityRepository, never()).save(any());

    // Verify the cache was not updated
    verify(cache, never()).putCache(anyString(), any());
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
