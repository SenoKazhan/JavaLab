package com.projects.countrycode.service;

import com.projects.countrycode.domain.City;
import java.util.List;
import org.springframework.stereotype.Service;

/** The interface City service. */
@Service
public interface CityService {
  /**
   * Find all cities list.
   *
   * @return the list
   */
  List<City> findAllCities();
  void saveBulk(List<City> cityList, Integer countryId);
  /**
   * Create.
   *
   * @param cityRequest the city request
   * @param countryId the country id
   */
  void save(City cityRequest, Integer countryId);

  /**
   * Update city.
   *
   * @param cityRequest the city request
   * @param id the id
   */
  void updateCity(City cityRequest, Integer id);

  /**
   * Gets city by id.
   *
   * @param id the id
   * @return the city by id
   */
  City getCityById(Integer id);

  /**
   * Delete city.
   *
   * @param id the id
   */
  void deleteCity(Integer id);
}
