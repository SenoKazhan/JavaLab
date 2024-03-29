package com.projects.countrycode.service;

import com.projects.countrycode.domain.Language;
import com.projects.countrycode.repodao.LanguageRepository;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class LanguageServiceImp implements LanguageService {
  private final LanguageRepository languageRepository;

  public LanguageServiceImp(LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
  }

  @Override
  public List<Language> getAllLanguages() {
    return languageRepository.findAll();
  }

  @Override
  public void updateLanguage(Language language) {
    languageRepository.save(language);
  }
}
