package com.projects.countrycode.service;

import com.projects.countrycode.domain.EntityCountries;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Service: Содержит бизнес-логику приложения и управляет операциями между контроллером и DAO.
@Service
public interface ServicePhone {
    List<EntityCountries> findAllCountries(); //Возвращает список
    Optional<EntityCountries> findById(Long id); //Возвращает объект - psql объекто-реляционная - по id.
    EntityCountries findByName(String countryName);
    List<EntityCountries> findByPhoneCode(Long code);
    EntityCountries saveCountry(EntityCountries countryData);
    EntityCountries updateCountry(EntityCountries countryData);
    void deleteDataAboutCountry(Long id); //Метод ничего не возвращает, поэтому void.
}
