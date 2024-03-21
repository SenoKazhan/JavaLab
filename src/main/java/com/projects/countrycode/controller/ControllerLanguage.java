package com.projects.countrycode.controller;

import com.projects.countrycode.domain.Language;
import com.projects.countrycode.service.ServiceLanguage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class ControllerLanguage {
    private final ServiceLanguage serviceLanguage;

    public ControllerLanguage(ServiceLanguage serviceLanguage) {
        this.serviceLanguage = serviceLanguage;
    }

    @GetMapping
        public List<Language> findAllLanguages() {
            return serviceLanguage.getAllLanguages();
        }

        @GetMapping("/{id}")
        public Language findLanguageById(@PathVariable("id") Integer id) {
            return serviceLanguage.getLanguageById(id);
        }

        @PostMapping
        public String saveLanguage(@RequestBody Language language) {
            serviceLanguage.saveLanguage(language);
            return "Language saved successfully";
        }

        @PutMapping("/{id}")
        public String updateLanguage(@PathVariable Integer id, @RequestBody Language language) {
            serviceLanguage.updateLanguage(language);
            return "Language updated successfully";
        }

        @DeleteMapping("/{id}")
        public String deleteLanguage(@PathVariable("id") Integer id) {
            serviceLanguage.deleteLanguage(id);
            return "Language deleted successfully";
        }
    }
