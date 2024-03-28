package com.projects.countrycode.controller;

// Controller: Отвечает за обработку запросов от клиента и взаимодействие с пользовательским
// интерфейсом.

import com.projects.countrycode.domain.Country;
import com.projects.countrycode.domain.Language;
import com.projects.countrycode.dto.CountryDto;
import com.projects.countrycode.repodao.CountryRepository;
import com.projects.countrycode.service.ServicePhone;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/countries")
public class ControllerPhone {
  private final ServicePhone servicePhone;
  private final CountryRepository countryRepository;

  public ControllerPhone(ServicePhone servicePhone, CountryRepository countryRepository) {
    this.servicePhone = servicePhone;
    this.countryRepository = countryRepository;
  }

  @GetMapping
  public List<Country> findAllCountries() {
    return servicePhone.findAllCountries();
  }

  @GetMapping("/getCountryInfo")
  public Country getCountryInfo(
      @RequestParam(name = "name", defaultValue = "null") String countryName) {
    return servicePhone.findByName(countryName);
  } // Предметное + сервис + ДТО service phone

  @GetMapping("/getCodeInfo")
  public List<Country> getCountryInfo(
      @RequestParam(name = "phonecode", defaultValue = "0") Long code) {
    return servicePhone.findByPhoneCode(code);
  }


  @GetMapping("/{id}")
  public ResponseEntity<CountryDto> findCountryById(@PathVariable("id") Integer id) {
    Optional<Country> countryOptional = countryRepository.findById(id);
    if (countryOptional.isPresent()) {
      Country country = countryOptional.get();
      CountryDto countryDto = new CountryDto();
      countryDto.setId(country.getId());
      countryDto.setName(country.getName());
      countryDto.setCode(country.getCode());
      countryDto.setPhone(country.getPhone());
      List<String> languageNames = country.getLanguages().stream().map(Language::getName).toList();
      countryDto.setLanguages(languageNames);
      return ResponseEntity.ok(countryDto);
    } else {
      throw new ResourceNotFoundException("Country not found with id " + id);
    }
  }

  @PostMapping("/alc/{countryId}")
  public ResponseEntity<String> addLanguageToCountry(
      @PathVariable("countryId") Integer countryId, @RequestBody Language language) {
    Optional<Country> countryOptional = countryRepository.findById(countryId);
    if (countryOptional.isPresent()) {
      Country country = countryOptional.get();
      country.addLanguage(language);
      countryRepository.save(country);
      return ResponseEntity.ok("Language added to Country successfully");
    } else {
      throw new ResourceNotFoundException("Country not found with id " + countryId);
    }
  }

  @PostMapping
  public String saveCountry(@RequestBody Country entityCountries) {
    servicePhone.saveCountry(entityCountries);
    return "Saved successfully";
  }

  @PutMapping("/{id}")
  public String updateCountry(
      @PathVariable("id") Integer id, @RequestBody Country entityCountries) {
    Optional<Country> countryOptional = countryRepository.findById(id);
    if (countryOptional.isPresent()) {
      Country country = countryOptional.get();
      country.setName(entityCountries.getName());
      countryRepository.save(country);
      return "Updated successfully";
    } else {
      return "Failed";
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Country> deleteCountry(@PathVariable("id") Integer id) {
    Optional<Country> countryOptional = countryRepository.findById(id);
    if (countryOptional.isPresent()) {
      Country country = countryOptional.get();
      country.getLanguages().forEach(language -> language.getCountries().remove(country));
      countryRepository.delete(country);
      return ResponseEntity.ok().build();
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
