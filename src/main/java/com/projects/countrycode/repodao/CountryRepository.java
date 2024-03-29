package com.projects.countrycode.repodao;

import com.projects.countrycode.domain.Country;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// DAO: Предоставляет интерфейс для доступа к данным в базе данных.

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> { // Id as primary key
  Optional<Country> findByName(String name);

  List<Country> findByPhone(Long phone);
}
