package com.projects.countrycode.service;
import com.projects.countrycode.domain.Country;
import org.springframework.stereotype.Service;

import java.util.List;

//Service: Содержит бизнес-логику приложения и управляет операциями между контроллером и DAO.
@Service
public interface ServicePhone {
    List<Country> findAllCountries(); //Возвращает список
    Country findById(Integer id); //Возвращает объект - psql объекто-реляционная - по id.
    Country findByName(String countryName);
    List<Country> findByPhoneCode(Long code);
    void saveCountry(Country countryData);
    void updateCountry(Country countryData);
    void updateCountryName(Integer countryId, String newName);
    void deleteDataAboutCountry(Integer id); //Метод ничего не возвращает, поэтому void.
}
