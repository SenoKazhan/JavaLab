package com.projects.countrycode.controller;

// Controller: Отвечает за обработку запросов от клиента и взаимодействие с пользовательским
// интерфейсом.

import com.projects.countrycode.domain.Country;
import com.projects.countrycode.domain.Language;
import com.projects.countrycode.dto.CountryDto;
import com.projects.countrycode.repodao.CountryRepository;
import com.projects.countrycode.repodao.LanguageRepository;
import com.projects.countrycode.service.PhoneService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/** The type Controller phone. */
@CrossOrigin
@RestController
@RequestMapping("/api/countries")
public class ControllerPhone {
  private final PhoneService phoneService;
  private final CountryRepository countryRepository;
  private final LanguageRepository languageRepository;

  /**
   * Instantiates a new Controller phone.
   *
   * @param phoneService the phone service
   * @param countryRepository the country repository
   * @param languageRepository the language repository
   */
  public ControllerPhone(
      PhoneService phoneService,
      CountryRepository countryRepository,
      LanguageRepository languageRepository) {
    this.phoneService = phoneService;
    this.countryRepository = countryRepository;
    this.languageRepository = languageRepository;
  }

  /**
   * Find all countries list.
   *
   * @return the list
   */
  @GetMapping
  public List<Country> findAllCountries() {
    return phoneService.findAllCountries();
  }

  /**
   * Gets country info.
   *
   * @param countryName the country name
   * @return the country info
   */
  @GetMapping("/getCountryInfo")
  public Optional<Country> getCountryInfo(
      @RequestParam(name = "name", defaultValue = "null") String countryName) {
    return countryRepository.findByName(countryName);
  } // Предметное + сервис + ДТО service phone

  /**
   * Gets country info.
   *
   * @param code the code
   * @return the country info
   */
  @GetMapping("/getCodeInfo")
  public List<Country> getCountryInfo(
      @RequestParam(name = "phonecode", defaultValue = "0") Long code) {
    List<Country> countries = phoneService.findByPhoneCode(code);
    if (countries.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested resource is not found.");
    }
    return countries;
  }

  /**
   * Find country by id response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @GetMapping("/{id}")
  public ResponseEntity<CountryDto> findCountryById(@PathVariable("id") Integer id) {
    Country country = phoneService.findById(id);
    CountryDto countryDto = new CountryDto();
    countryDto.setId(country.getId());
    countryDto.setName(country.getName());
    countryDto.setCode(country.getCode());
    countryDto.setPhone(country.getPhone());
    List<String> languageNames = country.getLanguages().stream().map(Language::getName).toList();
    countryDto.setLanguages(languageNames);
    return ResponseEntity.ok(countryDto);
  }

  /**
   * Add language to country response entity.
   *
   * @param countryId the country id
   * @param language the language
   * @return the response entity
   */
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
        phoneService.saveCountry(country);
        return ResponseEntity.ok("Новый язык успешно создан и добавлен к стране");
      }
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Save country string.
   *
   * @param entityCountries the entity countries
   * @return the string
   */
  @Transactional
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public String createCountry(@RequestBody Country entityCountries) {
    phoneService.saveCountry(entityCountries);
    return "Saved successfully";
  }

  /**
   * Update country response entity.
   *
   * @param id the id
   * @param entityCountries the entity countries
   * @return the response entity
   */
  @Transactional
  @ResponseStatus(HttpStatus.OK)
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
      phoneService.saveCountry(country);
      return ResponseEntity.ok("Updated successfully");
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Delete country response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<Country> deleteCountry(@PathVariable("id") Integer id) {
    Optional<Country> countryOptional = countryRepository.findById(id);
    if (countryOptional.isPresent()) {
      Country country = countryOptional.get();
      country.getLanguages().forEach(language -> language.getCountries().remove(country));
      phoneService.deleteCountry(id);
      return ResponseEntity.ok().build();
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
