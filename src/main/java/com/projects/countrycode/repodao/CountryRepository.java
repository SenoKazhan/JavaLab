package com.projects.countrycode.repodao;

import com.projects.countrycode.domain.Country;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// DAO: Предоставляет интерфейс для доступа к данным в базе данных.

/** The interface Country repository. */
@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> { // Id as primary key
  /**
   * Find by name optional.
   *
   * @param name the name
   * @return the optional
   */
  Optional<Country> findByName(String name);

  /**
   * Find countries by language id list.
   *
   * @param languageId the language id
   * @return the list
   */
  @Query("SELECT c FROM Country c JOIN c.languages l WHERE l.id = ?1")
  List<Country> findCountriesByLanguageId(Integer languageId);

  /**
   * Find by phone list.
   *
   * @param phone the phone
   * @return the list
   */
  List<Country> findByPhone(Long phone);
}
