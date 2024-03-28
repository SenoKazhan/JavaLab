package com.projects.countrycode.service;

import com.projects.countrycode.domain.Country;
import org.springframework.stereotype.Service;

import java.util.List;

// Service: Содержит бизнес-логику приложения и управляет операциями между контроллером и DAO.
@Service
public interface ServicePhone {
  List<Country> findAllCountries(); // Возвращает список

  Country findByName(String countryName);

  List<Country> findByPhoneCode(Long phone);

  void saveCountry(Country countryData);

  void updateCountryName(Integer countryId, String newName);

}
