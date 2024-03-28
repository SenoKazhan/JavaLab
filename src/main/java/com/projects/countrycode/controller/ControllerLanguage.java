package com.projects.countrycode.controller;

import com.projects.countrycode.domain.Language;
import com.projects.countrycode.repodao.LanguageRepository;
import com.projects.countrycode.service.ServiceLanguage;
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
  private final ServiceLanguage serviceLanguage;
  private final LanguageRepository languageRepository;

  public ControllerLanguage(
      ServiceLanguage serviceLanguage, LanguageRepository languageRepository) {
    this.serviceLanguage = serviceLanguage;
    this.languageRepository = languageRepository;
  }

  @GetMapping
  public List<Language> findAllLanguages() {
    return serviceLanguage.getAllLanguages();
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

  @PutMapping("/{id}")
  public String updateLanguage(@PathVariable Integer id, @RequestBody Language language) {
    serviceLanguage.updateLanguage(language);
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
