package com.projects.countrycode.controller;

import com.projects.countrycode.domain.Country;
import com.projects.countrycode.domain.Language;
import com.projects.countrycode.repodao.CountryRepository;
import com.projects.countrycode.repodao.LanguageRepository;
import com.projects.countrycode.service.LanguageService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** The type Controller language. */
@RestController
@RequestMapping("/api/languages")
public class ControllerLanguage {
  private final LanguageService languageService;
  private final LanguageRepository languageRepository;
  private final CountryRepository countryRepository;

  /**
   * Instantiates a new Controller language.
   *
   * @param languageService the language service
   * @param languageRepository the language repository
   * @param countryRepository the country repository
   */
  public ControllerLanguage(
      LanguageService languageService,
      LanguageRepository languageRepository,
      CountryRepository countryRepository) {
    this.languageService = languageService;
    this.languageRepository = languageRepository;
    this.countryRepository = countryRepository;
  }

  /**
   * Find all languages list.
   *
   * @return the list
   */
  @GetMapping
  public List<Language> findAllLanguages() {
    return languageService.getAllLanguages();
  }

  /**
   * Find language by id optional.
   *
   * @param id the id
   * @return the optional
   */
  @GetMapping("/{id}")
  public Language findLanguageById(@PathVariable("id") Integer id) {
    return languageService.getLanguageById(id);
  }

  /**
   * Find cou by lang list.
   *
   * @param id the id
   * @return the list
   */
  @GetMapping("/{id}/c")
  public List<Country> findCouByLang(@PathVariable("id") Integer id) {
    return countryRepository.findCountriesByLanguageId(id);
  }

  /**
   * Save language string.
   *
   * @param language the language
   * @return the string
   */
  @Transactional
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public String createLanguage(@RequestBody Language language) {
    languageService.save(language);
    return "Language saved successfully";
  }

  /**
   * Add country to language response entity.
   *
   * @param languageId the language id
   * @param country the country
   * @return the response entity
   */
  @PostMapping("/alc/{languageId}")
  public ResponseEntity<String> addCountryToLanguage(
      @PathVariable("languageId") Integer languageId, @RequestBody Country country) {
    Optional<Language> languageOptional = languageRepository.findById(languageId);
    if (languageOptional.isPresent()) {
      Language language = languageOptional.get();
      Optional<Country> existingCountryOptional = countryRepository.findByName(country.getName());
      if (existingCountryOptional.isPresent()) {
        Country existingCountry = existingCountryOptional.get();
        language.addCountry(existingCountry);
        languageService.save(language);
        return ResponseEntity.ok("Страна успешно добавлена к языку");
      } else {
        countryRepository.save(country);
        language.addCountry(country);
        languageService.save(language);
        return ResponseEntity.ok("Новая страна успешно создана и добавлена к языку");
      }
    } else {
      throw new ResourceNotFoundException("Язык с идентификатором " + languageId + " не найден");
    }
  }

  /**
   * Update language string.
   *
   * @param id the id
   * @param language the language
   * @return the string
   */
  @Transactional
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
  public String updateLanguage(@PathVariable Integer id, @RequestBody Language language) {
    languageService.update(language, id);
    return "Language updated successfully";
  }

  /**
   * Delete language response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Language> deleteLanguage(@PathVariable Integer id) {
    Optional<Language> languageOptional;
    languageOptional = languageRepository.findById(id);
    if (languageOptional.isPresent()) {
      Language language = languageOptional.get();
      language.getCountries().forEach(country -> country.getLanguages().remove(language));
      languageService.deleteLanguage(id);
      return ResponseEntity.ok().build();
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
