package com.projects.countrycode.controller;

// Controller: Отвечает за обработку запросов от клиента и взаимодействие с пользовательским
// интерфейсом.

import com.projects.countrycode.domain.Country;
import com.projects.countrycode.domain.Language;
import com.projects.countrycode.dto.CountryDto;
import com.projects.countrycode.repodao.CountryRepository;
import com.projects.countrycode.repodao.LanguageRepository;
import com.projects.countrycode.service.PhoneService;
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
  private final PhoneService phoneService;
  private final CountryRepository countryRepository;
  private final LanguageRepository languageRepository;

  public ControllerPhone(
      PhoneService phoneService,
      CountryRepository countryRepository,
      LanguageRepository languageRepository) {
    this.phoneService = phoneService;
    this.countryRepository = countryRepository;
    this.languageRepository = languageRepository;
  }

  @GetMapping
  public List<Country> findAllCountries() {
    return phoneService.findAllCountries();
  }

  @GetMapping("/getCountryInfo")
  public Optional<Country> getCountryInfo(
      @RequestParam(name = "name", defaultValue = "null") String countryName) {
    return countryRepository.findByName(countryName);
  } // Предметное + сервис + ДТО service phone

  @GetMapping("/getCodeInfo")
  public List<Country> getCountryInfo(
      @RequestParam(name = "phonecode", defaultValue = "0") Long code) {
    return phoneService.findByPhoneCode(code);
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
      Optional<Language> existingLanguageOptional =
          languageRepository.findByName(language.getName());
      if (existingLanguageOptional.isPresent()) {
        Language existingLanguage = existingLanguageOptional.get();
        country.addLanguage(existingLanguage);
        countryRepository.save(country);
        return ResponseEntity.ok("Язык успешно добавлен к стране");
      } else {
        languageRepository.save(language);
        country.addLanguage(language);
        countryRepository.save(country);
        return ResponseEntity.ok("Новый язык успешно создан и добавлен к стране");
      }
    } else {
      throw new ResourceNotFoundException("Страна с идентификатором " + countryId + " не найдена");
    }
  }

  @PostMapping
  public String saveCountry(@RequestBody Country entityCountries) {
    phoneService.saveCountry(entityCountries);
    return "Saved successfully";
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateCountry(
      @PathVariable("id") Integer id, @RequestBody Country entityCountries) {
    Optional<Country> countryOptional = countryRepository.findById(id);
    if (countryOptional.isPresent()) {
      Country country = countryOptional.get();
      if (entityCountries.getName() != null) {
        country.setName(entityCountries.getName());
      }
      if (entityCountries.getPhone() != null) {
        country.setPhone(entityCountries.getPhone());
      }
      if (entityCountries.getCode() != null) {
        country.setCode(entityCountries.getCode());
      }
      countryRepository.save(country);
      return ResponseEntity.ok("Updated successfully");
    } else {
      return ResponseEntity.ok("The country wasn't found");
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
