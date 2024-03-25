package com.projects.countrycode.repodao;

import com.projects.countrycode.domain.City;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByCountryId(Long countryId);
    @Transactional
    void deleteByCountryId(Long countryId);
    City save(City city);
}
