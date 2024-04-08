package com.projects.countrycode.service;

import com.projects.countrycode.domain.Language;
import java.util.List;
import org.springframework.stereotype.Service;

/** The interface Language service. */
@Service
public interface LanguageService {
  /**
   * Gets all languages.
   *
   * @return the all languages
   */
  List<Language> getAllLanguages();

  /**
   * Update language.
   *
   * @param language the language
   */
  void save(Language language);

  /**
   * Update.
   *
   * @param langRequest the lang request
   * @param id the id
   */
  void update(Language langRequest, Integer id);

  /**
   * Gets language by id.
   *
   * @param id the id
   * @return the language by id
   */
  Language getLanguageById(Integer id);

  /**
   * Delete language.
   *
   * @param id the id
   */
  void deleteLanguage(Integer id);
}
