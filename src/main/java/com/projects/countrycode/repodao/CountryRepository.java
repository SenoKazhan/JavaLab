package com.projects.countrycode.repodao;

import com.projects.countrycode.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//DAO: Предоставляет интерфейс для доступа к данным в базе данных.

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> { //Id as primary key
    Country findByCountryName(String countryName);
    List<Country> findByPhoneCode(Long phoneCode);

}

