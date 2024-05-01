package com.projects.countrycode.service;

import com.projects.countrycode.component.Cache;
import com.projects.countrycode.domain.Country;
import com.projects.countrycode.repodao.CountryRepository;
import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** The type Phone service imp. */
@Primary
@Service
public class PhoneServiceImp implements PhoneService {
  private final CountryRepository repoDao;
  private static final String CACHE_KEY = "country-";
  private final Cache cache;

  /**
   * Instantiates a new Phone service imp.
   *
   * @param repoDao the repo dao
   */
  public PhoneServiceImp(CountryRepository repoDao, Cache cache) {
    this.repoDao = repoDao;
    this.cache = cache;
  }

  @Override
  public Country findById(Integer id) {
    return Optional.ofNullable(cache.getCache(CACHE_KEY + id))
        .map(Country.class::cast)
        .orElseGet(
            () -> {
              Country country =
                  repoDao
                      .findById(id)
                      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
              cache.putCache(CACHE_KEY + id, country);
              return country;
            });
  }

  @Override
  public List<Country> findAllCountries() {
    return repoDao.findAll();
  }

  @Override
  public void saveCountry(Country countryData) {
    cache.putCache(CACHE_KEY + countryData.getId(), countryData);
    repoDao.save(countryData);
  }

  @Override
  public void updateCountry(Country countryData, Integer id) {
    Country country;
    if (cache.containsKey(CACHE_KEY + id)) {
      country = (Country) cache.getCache(CACHE_KEY + id);
    } else {
      country =
          repoDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    country.setName(countryData.getName());
    repoDao.save(country);

    cache.putCache(CACHE_KEY + id, country);
    repoDao.save(countryData);
  }

  @Override
  public List<Country> findByPhoneCode(Long phone) {
    List<Country> countries = repoDao.findAll().stream()
            .filter(country -> country.getPhone().equals(phone))
            .toList();
    // Загружаем список городов для каждой найденной страны
    countries.forEach(country -> {
      Hibernate.initialize(country.getCities());
      // Устанавливаем список городов для каждой найденной страны
      country.setCities(country.getCities());
    });
    return countries;
  }


  @Override
  public void deleteCountry(Integer id) {
    repoDao.deleteById(id);
    cache.remove(CACHE_KEY + id);
  }
}
