package com.projects.countrycode.service;

import com.projects.countrycode.component.Cache;
import com.projects.countrycode.domain.City;
import com.projects.countrycode.domain.Country;
import com.projects.countrycode.repodao.CityRepository;
import com.projects.countrycode.repodao.CountryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** The type City service imp. */
@Primary
@Service
public class CityServiceImp implements CityService {
  private static final String CACHE_KEY = "city-";
  private final CityRepository cityRepository;
  private final CountryRepository countryRepository;
  private final Cache cache;

  /**
   * * * Instantiates a new City service imp.
   *
   * @param cityRepository the city repository
   * @param countryRepository the country repository
   * @param cache the cache
   */
  public CityServiceImp(
      CityRepository cityRepository, CountryRepository countryRepository, Cache cache) {
    this.cityRepository = cityRepository;
    this.countryRepository = countryRepository;
    this.cache = cache;
  }

  /**
   * Save.
   *
   * @param cityRequest the city request
   * @param countryId the country id
   */
  @Override
  public void save(City cityRequest, Integer countryId) {
    Optional<Country> countryOptional = countryRepository.findById(countryId);

    if (countryOptional.isPresent()) {
      Country country = countryOptional.get();
      cityRequest.setCountry(country);

      cityRepository.save(cityRequest);
      cache.putCache(CACHE_KEY + cityRequest.getId(), cityRequest);
    } else {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
  }

  /**
   * Save bulk.
   *
   * @param cityList the city list
   * @param countryId the country id
   */
  @Override
  public void saveBulk(List<City> cityList, Integer countryId) {
    Optional<Country> countryOptional = countryRepository.findById(countryId);

    if (countryOptional.isPresent()) {
      Country country = countryOptional.get();

      cityList.forEach(
          city -> {
            city.setCountry(country);
            cityRepository.save(city);
            cache.putCache(CACHE_KEY + city.getId(), city);
          });

    } else {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
  }

  @Override
  public List<City> findAllCities() {
    return cityRepository.findAll();
  }

  @Override
  public City getCityById(Integer id) {
    if (cache.containsKey(CACHE_KEY + id)) {
      return (City) cache.getCache(CACHE_KEY + id);
    }
    City city =
        cityRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    cache.putCache(CACHE_KEY + id, city);
    return cityRepository.findById(id).orElse(null);
  }

  @Override
  public void updateCity(City cityRequest, Integer id) {
    City city;
    if (cache.containsKey(CACHE_KEY + id)) {
      city = (City) cache.getCache(CACHE_KEY + id);
    } else {
      city =
          cityRepository
              .findById(id)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    city.setName(cityRequest.getName());
    cityRepository.save(city);

    cache.putCache(CACHE_KEY + id, city);
  }

  @Override
  public void deleteCity(Integer id) {
    cityRepository.deleteById(id);
    cache.remove(CACHE_KEY + id);
  }
}
