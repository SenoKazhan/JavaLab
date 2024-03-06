package com.projects.countrycode.repodao;

import com.projects.countrycode.domain.EntityCountries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//DAO: Предоставляет интерфейс для доступа к данным в базе данных.

@Repository
public interface Repodao extends JpaRepository<EntityCountries, Long> { //Id as primary key
    EntityCountries findByCountryName(String countryName);
    List<EntityCountries> findByPhoneCode(Long phoneCode);

}

