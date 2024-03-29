package com.projects.countrycode.service;

import com.projects.countrycode.domain.City;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CityService {
  List<City> findAllCities();

  City getCityById(Integer id);

  void deleteCity(Integer id);
}
