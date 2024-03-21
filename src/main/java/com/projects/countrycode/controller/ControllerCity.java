package com.projects.countrycode.controller;

import com.projects.countrycode.domain.City;
import com.projects.countrycode.repodao.CityRepository;
import com.projects.countrycode.service.ServiceCity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ControllerCity {
    private final ServiceCity serviceCity;
    private final CityRepository cityRepository;

    public ControllerCity(ServiceCity serviceCity, CityRepository cityRepository) {
        this.serviceCity = serviceCity;
        this.cityRepository = cityRepository;
    }

    @GetMapping("/cities")
    public List<City> findAllCities() {
        return serviceCity.findAllCities();
    }

    @GetMapping("/cities/{id}")
    public City findCityById(@PathVariable("id") Integer id) {
        return serviceCity.getCityById(id);
    }

    @GetMapping("countries/{tutorialId}/cities")
    public ResponseEntity<List<City>> getAllCitiesById(@PathVariable(value = "tutorialId") Long tutorialId) {
        List<City> cities = cityRepository.findByCountryId(tutorialId);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
    @PostMapping
    public String saveCity(@RequestBody City city) {
        serviceCity.saveCity(city);
        return "Language saved successfully";
    }

    @PutMapping("/{id}")
    public String updateCity(@PathVariable String id, @RequestBody City city) {
        serviceCity.updateCity(city);
        return "Language updated successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteLanguage(@PathVariable("id") Integer id) {
        serviceCity.deleteCity(id);
        return "Language deleted successfully";
    }
}
