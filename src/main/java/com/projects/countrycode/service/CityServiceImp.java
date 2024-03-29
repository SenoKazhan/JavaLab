package com.projects.countrycode.service;

import com.projects.countrycode.domain.City;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.projects.countrycode.repodao.CityRepository;

import java.util.List;

@Primary
@Service
public class ServiceCityImp implements ServiceCity {
  private final CityRepository cityRepository;

  public ServiceCityImp(CityRepository cityRepository) {
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
