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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

/** The type Controller phone. */
@Controller
@RequestMapping("/api/countries")
public class ControllerPhone {
  private static final String ERROR_METHOD = "errorMethod";
  private static final String SUCCESS_METHOD = "successMethod";
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

  @GetMapping
  public String findAllCountries(Model model) {
    List<Country> countries = phoneService.findAllCountries();
    model.addAttribute("countries", countries);
    return "countries";
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

  @GetMapping("/save")
  public String showSaveCountryForm(final Model model) {
    model.addAttribute("country", new Country());
    return "saveCountry";
  }

  @PostMapping("/save")
  public String createCountry(@ModelAttribute Country entityCountries) {
    boolean checkError = phoneService.saveCountry(entityCountries);
    if (checkError) {
      return SUCCESS_METHOD;
    } else {
      return ERROR_METHOD;
    }
  }

  @GetMapping("/update")
  public String showUpdateCountryForm(final Model model) {
    model.addAttribute("country", new Country());
    return "updateCountry";
  }

  @PostMapping("/update")
  public String updateCountry(
      @RequestParam(required = false, name = "id") Integer id,
      @ModelAttribute(name = "country") Country updatedCountry) {
    Optional<Country> countryOptional = countryRepository.findById(id);
    if (countryOptional.isPresent()) {
      Country country = countryOptional.get();
      if (updatedCountry.getName() != null) {
        country.setName(updatedCountry.getName());
      }
      if (updatedCountry.getPhone() != null) {
        country.setPhone(updatedCountry.getPhone());
      }
      if (updatedCountry.getCode() != null) {
        country.setCode(updatedCountry.getCode());
      }
      phoneService.saveCountry(country);
      return SUCCESS_METHOD;
    } else return ERROR_METHOD;
    }

  @GetMapping("/delete")
  public String getDeleteCountry(@RequestParam(name="id") Integer id) {
    return "redirect:/api/countries";
  }

  @Transactional
  @PostMapping("/delete")
  public String deleteCountry(@RequestParam(name="id") Integer id) {
    Optional<Country> countryOptional = countryRepository.findById(id);
    if (countryOptional.isPresent()) {
      Country country = countryOptional.get();
      country.getLanguages().forEach(language -> language.getCountries().remove(country));
      if (phoneService.deleteCountry(id)) {
        return SUCCESS_METHOD;
      }
    }
    return ERROR_METHOD;
  }
}
