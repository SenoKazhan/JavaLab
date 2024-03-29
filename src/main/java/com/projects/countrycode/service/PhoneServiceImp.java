package com.projects.countrycode.service;

import com.projects.countrycode.domain.Country;
import com.projects.countrycode.repodao.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class ServicePhoneImp implements ServicePhone {
  private final CountryRepository repoDao;

  public ServicePhoneImp(CountryRepository repoDao) {
    this.repoDao = repoDao;
  }

  @Override
  public List<Country> findAllCountries() {
    return repoDao.findAll();
  }

  @Override
  public void saveCountry(Country countryData) {
    repoDao.save(countryData);
  }

  @Override
  public Country findByName(String countryName) {
    return repoDao.findByName(countryName);
  }
  @Override
  public List<Country> findByPhoneCode(Long phone) {
    return repoDao.findByPhone(phone);
  }

  @Override // @
  public void updateCountryName(Integer countryId, String newName) {
    Country country =
        repoDao
            .findById(countryId)
            .orElseThrow(() -> new EntityNotFoundException("Country not found"));
    country.setName(newName);
    repoDao.save(country);
  }
}
