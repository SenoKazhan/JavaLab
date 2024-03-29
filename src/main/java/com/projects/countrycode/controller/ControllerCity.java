package com.projects.countrycode.controller;

import com.projects.countrycode.domain.City;
import com.projects.countrycode.domain.Country;
import com.projects.countrycode.repodao.CityRepository;
import com.projects.countrycode.repodao.CountryRepository;
import com.projects.countrycode.service.CityService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControllerCity {
  private final CityService cityService;
  private final CityRepository cityRepository;
  private final CountryRepository countryRepository;

  public ControllerCity(
      CityService cityService, CityRepository cityRepository, CountryRepository countryRepository) {
    this.cityService = cityService;
    this.cityRepository = cityRepository;
    this.countryRepository = countryRepository;
  }

  @GetMapping("/cities")
  public List<City> findAllCities() {
    return cityService.findAllCities();
  }

  @GetMapping("/cities/{id}")
  public City findCityById(@PathVariable("id") Integer id) {
    return cityService.getCityById(id);
  }

  @GetMapping("countries/{countryId}/cities")
  public List<City> getCitiesByCountry(@PathVariable Integer countryId) {
    return cityRepository.findCitiesByCountryId(countryId);
  }

  @PostMapping("countries/{countryId}/cities")
  public ResponseEntity<City> createCity(
      @PathVariable(value = "countryId") Integer countryId, @RequestBody City cityRequest) {
    Optional<Country> countryOptional = countryRepository.findById(countryId);

    if (countryOptional.isPresent()) {
      Country country = countryOptional.get();
      cityRequest.setCountry(country);

      City city = cityRepository.save(cityRequest);

      return new ResponseEntity<>(city, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("countries/{countryId}/cities/{id}")
  public ResponseEntity<City> updateCity(
      @PathVariable("id") Integer id,
      @PathVariable("countryId") Integer countryId,
      @RequestBody City commentRequest) {
    Optional<City> cityOptional = cityRepository.findById(id);
    if (cityOptional.isPresent()) {
      City city = cityOptional.get();
      city.setName(commentRequest.getName());
      cityRepository.save(city);
      return new ResponseEntity<>(cityRepository.save(city), HttpStatus.OK);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("cities/{id}")
  public String deleteLCity(@PathVariable("id") Integer id) {
    cityService.deleteCity(id);
    return "City deleted successfully";
  }

  @DeleteMapping("/countries/{tutorialId}/cities")
  public ResponseEntity<List<City>> deleteAllCommentsOfCountry(
      @PathVariable(value = "tutorialId") Long tutorialId) {
    cityRepository.deleteByCountryId(tutorialId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
