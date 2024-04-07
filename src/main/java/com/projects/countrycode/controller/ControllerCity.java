package com.projects.countrycode.controller;

import com.projects.countrycode.domain.City;
import com.projects.countrycode.repodao.CityRepository;
import com.projects.countrycode.service.CityService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** The type Controller city. */
@RestController
@RequestMapping("/api")
public class ControllerCity {
  private final CityService cityService;
  private final CityRepository cityRepository;

  /**
   * Instantiates a new Controller city.
   *
   * @param cityService the city service
   * @param cityRepository the city repository
   */
  public ControllerCity(CityService cityService, CityRepository cityRepository) {
    this.cityService = cityService;
    this.cityRepository = cityRepository;
  }

  /**
   * Find all cities list.
   *
   * @return the list
   */
  @GetMapping("/cities")
  public List<City> findAllCities() {
    return cityService.findAllCities();
  }

  /**
   * Find city by id city.
   *
   * @param id the id
   * @return the city
   */
  @GetMapping("/cities/{id}")
  public City findCityById(@PathVariable("id") Integer id) {
    return cityService.getCityById(id);
  }

  /**
   * Gets cities by country.
   *
   * @param countryId the country id
   * @return the cities by country
   */
  @GetMapping("countries/{countryId}/cities")
  public List<City> getCitiesByCountry(@PathVariable Integer countryId) {
    return cityRepository.findCitiesByCountryId(countryId);
  }

  /**
   * Create city string.
   *
   * @param countryId the country id
   * @param cityRequest the city request
   * @return the string
   */
  @Transactional
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("countries/{countryId}/cities")
  public String createCity(
      @PathVariable(value = "countryId") Integer countryId, @RequestBody City cityRequest) {
    cityService.save(cityRequest, countryId);
    return "New city was added";
  }

  /**
   * Update city.
   *
   * @param id the id
   * @param countryId the country id
   * @param commentRequest the comment request
   */
  @Transactional
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("countries/{countryId}/cities/{id}")
  public void updateCity(
      @PathVariable("id") Integer id,
      @PathVariable("countryId") Integer countryId,
      @RequestBody City commentRequest) {
    cityService.updateCity(commentRequest, id);
  }

  /**
   * Delete city string.
   *
   * @param id the id
   * @return the string
   */
  @Transactional
  @DeleteMapping("cities/{id}")
  public String deleteCity(@PathVariable("id") Integer id) {
    cityService.deleteCity(id);
    return "City deleted successfully";
  }

  /**
   * Delete all comments of country response entity.
   *
   * @param tutorialId the tutorial id
   * @return the response entity
   */
  @Transactional
  @DeleteMapping("/countries/{tutorialId}/cities")
  public ResponseEntity<List<City>> deleteAllCommentsOfCountry(
      @PathVariable(value = "tutorialId") Integer tutorialId) {
    cityRepository.deleteByCountryId(tutorialId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
