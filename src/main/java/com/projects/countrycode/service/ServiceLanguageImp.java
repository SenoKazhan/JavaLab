package com.projects.countrycode.service;
import com.projects.countrycode.domain.Language;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.projects.countrycode.repodao.LanguageRepository;

import java.util.List;

@Primary
@Service
public class ServiceLanguageImp implements ServiceLanguage {
    private final LanguageRepository languageRepository;
    public ServiceLanguageImp(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }
    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }
    @Override
    public Language getLanguageById(Integer id) {
        return languageRepository.findById(id).orElse(null);
    }
   @Override
    public void saveLanguage(Language language) {
        languageRepository.save(language);
    }
    @Override
    public void updateLanguage(Language language) {
        languageRepository.save(language);
    }
    @Override
    public void deleteLanguage(Integer id) {
        languageRepository.deleteById(id);
    }
}
