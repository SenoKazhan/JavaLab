package com.projects.countrycode.repoDAO;

import com.projects.countrycode.Domain.EntityCountries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//DAO: Предоставляет интерфейс для доступа к данным в базе данных.

@Repository
public interface repoDao extends JpaRepository<EntityCountries, Long> { //Id as primary key
    List<EntityCountries> findByCountryName(String countryName);
}

