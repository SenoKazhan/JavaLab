package com.projects.countrycode.service;

import com.projects.countrycode.domain.City;
import com.projects.countrycode.repodao.CityRepository;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class CityServiceImp implements CityService {
  private final CityRepository cityRepository;

  public CityServiceImp(CityRepository cityRepository) {
    this.cityRepository = cityRepository;
  }

  @Override
  public List<City> findAllCities() {
    return cityRepository.findAll();
  }

  @Override
  public City getCityById(Integer id) {
    return cityRepository.findById(id).orElse(null);
  }

  @Override
  public void deleteCity(Integer id) {
    cityRepository.deleteById(id);
  }
}
