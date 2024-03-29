package com.projects.countrycode.service;

import com.projects.countrycode.domain.Language;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface LanguageService {
  List<Language> getAllLanguages();

  void updateLanguage(Language language);
}
