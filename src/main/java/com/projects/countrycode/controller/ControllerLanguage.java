package com.projects.countrycode.controller;

import com.projects.countrycode.domain.Country;
import com.projects.countrycode.domain.Language;
import com.projects.countrycode.repodao.CountryRepository;
import com.projects.countrycode.repodao.LanguageRepository;
import com.projects.countrycode.service.LanguageService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/languages")
public class ControllerLanguage {
  private final LanguageService languageService;
  private final LanguageRepository languageRepository;
  private final CountryRepository countryRepository;

  public ControllerLanguage(
      LanguageService languageService,
      LanguageRepository languageRepository,
      CountryRepository countryRepository) {
    this.languageService = languageService;
    this.languageRepository = languageRepository;
    this.countryRepository = countryRepository;
  }

  @GetMapping
  public List<Language> findAllLanguages() {
    return languageService.getAllLanguages();
  }

  @GetMapping("/{id}")
  public Optional<Language> findLanguageById(@PathVariable("id") Integer id) {
    return languageRepository.findById(id);
  }

  @PostMapping
  public String saveLanguage(@RequestBody Language language) {
    languageRepository.save(language);
    return "Language saved successfully";
  }

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
        languageRepository.save(language);
        return ResponseEntity.ok("Страна успешно добавлена к языку");
      } else {
        countryRepository.save(country);
        language.addCountry(country);
        languageRepository.save(language);
        return ResponseEntity.ok("Новая страна успешно создана и добавлена к языку");
      }
    } else {
      throw new ResourceNotFoundException("Язык с идентификатором " + languageId + " не найден");
    }
  }

  @PutMapping("/{id}")
  public String updateLanguage(@PathVariable Integer id, @RequestBody Language language) {
    languageService.updateLanguage(language);
    return "Language updated successfully";
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Language> deleteLanguage(@PathVariable Integer id) {
    Optional<Language> languageOptional;
    languageOptional = languageRepository.findById(id);
    if (languageOptional.isPresent()) {
      Language language = languageOptional.get();
      language.getCountries().forEach(country -> country.getLanguages().remove(language));
      languageRepository.delete(language);
      return ResponseEntity.ok().build();
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
