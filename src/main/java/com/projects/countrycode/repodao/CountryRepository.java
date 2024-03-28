package com.projects.countrycode.repodao;

import com.projects.countrycode.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// DAO: Предоставляет интерфейс для доступа к данным в базе данных.

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> { // Id as primary key
  Country findByName(String name);

  List<Country> findByPhone(Long phone);

}
