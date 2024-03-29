package com.projects.countrycode.service;

import com.projects.countrycode.domain.Country;
import java.util.List;
import org.springframework.stereotype.Service;

// Service: Содержит бизнес-логику приложения и управляет операциями между контроллером и DAO.
@Service
public interface PhoneService {
  List<Country> findAllCountries(); // Возвращает список

  List<Country> findByPhoneCode(Long phone);

  void saveCountry(Country countryData);

  void updateCountryName(Integer countryId, String newName);
}
