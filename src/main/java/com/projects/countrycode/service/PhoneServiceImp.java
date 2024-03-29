package com.projects.countrycode.service;

import com.projects.countrycode.domain.Country;
import com.projects.countrycode.repodao.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class PhoneServiceImp implements PhoneService {
  private final CountryRepository repoDao;

  public PhoneServiceImp(CountryRepository repoDao) {
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
