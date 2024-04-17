package com.projects.countrycode.repodao;

import com.projects.countrycode.domain.City;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** The interface City repository. */
@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
  /**
   * Find cities by country id list.
   *
   * @param countryId the country id
   * @return the list
   */
@Query("SELECT c FROM City c WHERE c.country.id = ?1")
  List<City> findCitiesByCountryId(Integer countryId);

  //   @Query(value = "SELECT * FROM city c WHERE c.country_id = ?1", nativeQuery = true)
  City save(City city);

  /**
   * Delete by country id.
   *
   * @param countryId the country id
   */
  @Transactional
  void deleteByCountryId(Integer countryId);
}
