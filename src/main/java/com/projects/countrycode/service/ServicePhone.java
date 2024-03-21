package com.projects.countrycode.service;
import com.projects.countrycode.domain.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Service: Содержит бизнес-логику приложения и управляет операциями между контроллером и DAO.
@Service
public interface ServicePhone {
    List<Country> findAllCountries(); //Возвращает список
    Optional<Country> findById(Long id); //Возвращает объект - psql объекто-реляционная - по id.
    Country findByName(String countryName);
    List<Country> findByPhoneCode(Long code);
    void saveCountry(Country countryData);
    void updateCountry(Country countryData);
    void updateCountryName(Long countryId, String newName);
    void deleteDataAboutCountry(Long id); //Метод ничего не возвращает, поэтому void.
}
