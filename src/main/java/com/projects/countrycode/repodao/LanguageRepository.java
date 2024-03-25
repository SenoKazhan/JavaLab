package com.projects.countrycode.repodao;

import com.projects.countrycode.domain.Country;
import com.projects.countrycode.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {

}

