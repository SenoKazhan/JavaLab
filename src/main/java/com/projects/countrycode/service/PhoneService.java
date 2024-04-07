package com.projects.countrycode.service;

import com.projects.countrycode.domain.Country;
import java.util.List;
import org.springframework.stereotype.Service;

/** The interface Phone service. */
// Service: Содержит бизнес-логику приложения и управляет операциями между контроллером и DAO.
@Service
public interface PhoneService {
  /**
   * Find all countries list.
   *
   * @return the list
   */
  List<Country> findAllCountries(); // Возвращает список

  /**
   * Find by id country.
   *
   * @param id the id
   * @return the country
   */
  public Country findById(Integer id);

  /**
   * Find by phone code list.
   *
   * @param phone the phone
   * @return the list
   */
  List<Country> findByPhoneCode(Long phone);

  /**
   * Save country.
   *
   * @param countryData the country data
   */
  void saveCountry(Country countryData);

  /**
   * Update country.
   *
   * @param countryData the country data
   * @param id the id
   */
  void updateCountry(Country countryData, Integer id);

  /**
   * Delete country.
   *
   * @param id the id
   */
  void deleteCountry(Integer id);
}
