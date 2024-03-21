package com.projects.countrycode.service;
import com.projects.countrycode.domain.Language;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ServiceLanguage {
    List<Language> getAllLanguages();
    Language getLanguageById(Integer id);
    void saveLanguage(Language language);
    void updateLanguage(Language language);
    void deleteLanguage(Integer id);
}
