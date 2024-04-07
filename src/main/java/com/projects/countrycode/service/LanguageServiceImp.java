package com.projects.countrycode.service;

import com.projects.countrycode.component.Cache;
import com.projects.countrycode.domain.Language;
import com.projects.countrycode.repodao.LanguageRepository;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** The type Language service imp. */
@Primary
@Service
public class LanguageServiceImp implements LanguageService {
  private final LanguageRepository languageRepository;

  private static final String CACHE_KEY = "lang-";

  private final Cache cache;

  /**
   * Instantiates a new Language service imp.
   *
   * @param languageRepository the language repository
   */
  public LanguageServiceImp(LanguageRepository languageRepository, Cache cache) {
    this.languageRepository = languageRepository;
    this.cache = cache;
  }

  @Override
  public Language getLanguageById(Integer id) {
    if (cache.containsKey(CACHE_KEY + id)) {
      return (Language) cache.getCache(CACHE_KEY + id);
    }
    Language language =
        languageRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    cache.putCache(CACHE_KEY + id, language);
    return languageRepository.findById(id).orElse(null);
  }

  @Override
  public List<Language> getAllLanguages() {
    return languageRepository.findAll();
  }

  @Override
  public void save(Language language) {
    cache.putCache(CACHE_KEY + language.getId(), language);
    languageRepository.save(language);
  }

  @Override
  public void update(Language langRequest, Integer id) {
    Language language;
    if (cache.containsKey(CACHE_KEY + id)) {
      language = (Language) cache.getCache(CACHE_KEY + id);
    } else {
      language =
          languageRepository
              .findById(id)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    language.setName(langRequest.getName());
    languageRepository.save(language);

    cache.putCache(CACHE_KEY + id, language);
  }

  @Override
  public void deleteLanguage(Integer id) {
    languageRepository.deleteById(id);
    cache.remove(CACHE_KEY + id);
  }
}
