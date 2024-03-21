package com.projects.countrycode.service;
import com.projects.countrycode.domain.City;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ServiceCity {
     List<City> findAllCities();
     City getCityById(Integer id);
     void saveCity(City city);
     void updateCity(City city);
     void deleteCity(Integer id);
}
