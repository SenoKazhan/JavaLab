package com.projects.countrycode.service;

import com.projects.countrycode.domain.EntityCountries;
import com.projects.countrycode.repodao.Repodao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceImp implements ServicePhone{
    private final Repodao repoDao;

    public ServiceImp(Repodao repoDao) {
        this.repoDao = repoDao;
    }

    @Override
    public List<EntityCountries> findAllCountries() {
        return repoDao.findAll();
    }

    @Override
    public Optional<EntityCountries> findById(Long id) {
        return repoDao.findById(id);
    }

    @Override
    public EntityCountries saveCountry(EntityCountries countryData) {
        return repoDao.save(countryData);
    }
    @Override
    public List<EntityCountries> findByName(String countryName){
        return repoDao.findByCountryName(countryName);
    }
    @Override
    public EntityCountries updateCountry(EntityCountries countryData) {
        return repoDao.save(countryData);
    }

    @Override
    public void deleteDataAboutCountry(Long id) {
        repoDao.deleteById(id);
    }
}
