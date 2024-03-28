package com.projects.countrycode.repodao;

import com.projects.countrycode.domain.City;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
  @Query("SELECT c FROM City c WHERE c.country.id = ?1")
  List<City> findCitiesByCountryId(Integer countryId);

  @Transactional
  void deleteByCountryId(Long countryId);

  City save(City city);
}
