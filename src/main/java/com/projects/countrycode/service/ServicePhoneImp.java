package com.projects.countrycode.service;
import com.projects.countrycode.domain.Country;
import com.projects.countrycode.repodao.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Optional<Country> findById(Long id) {
        return repoDao.findById(id);
    }
    @Override
    public void saveCountry(Country countryData) {
        repoDao.save(countryData);
    }
    @Override
    public Country findByName(String countryName){
        return repoDao.findByCountryName(countryName);
    }
    @Override
    public  List<Country> findByPhoneCode(Long code ) {
        return repoDao.findByPhoneCode(code);
    }
    @Override
    public void updateCountry(Country countryData) {
        repoDao.save(countryData);
    }
    @Override
    public void deleteDataAboutCountry(Long id) {
        repoDao.deleteById(id);
    }
    @Override //@
    public void updateCountryName(Long countryId, String newName) {
        Country country = repoDao.findById(countryId).orElseThrow(() -> new EntityNotFoundException("Country not found"));
        country.setCountryName(newName);
        repoDao.save(country);
    }

}
