package com.projects.countrycode.controller;
//Controller: Отвечает за обработку запросов от клиента и взаимодействие с пользовательским интерфейсом.

import com.projects.countrycode.domain.Country;
import com.projects.countrycode.service.ServicePhone;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/countries")
public class ControllerPhone {
    private final ServicePhone servicePhone;

    public ControllerPhone(ServicePhone servicePhone) {
        this.servicePhone = servicePhone;
    }

    @GetMapping
    public List<Country> findAllCountries(){
        return servicePhone.findAllCountries();
    }

    @GetMapping("/getCountryInfo")
    public Country getCountryInfo(@RequestParam(name = "name", defaultValue = "null") String countryName)
        {
            return servicePhone.findByName(countryName);
    } // Предметное + сервис + ДТО service phone

    @GetMapping("/getCodeInfo")
    public List<Country> getCountryInfo(@RequestParam(name = "phonecode", defaultValue = "0") Long code)
    {
       return servicePhone.findByPhoneCode(code);
    }

    @GetMapping("/{id}")
    public Optional<Country> findCountryById(@PathVariable("id") Long id){
        return servicePhone.findById(id);
    }

    @PostMapping
    public String saveCountry(@RequestBody Country entityCountries){
       servicePhone.saveCountry(entityCountries);
       return "Saved successfully";
    }

    @PutMapping
    public String updateCountry(@RequestBody Country entityCountries){
        servicePhone.updateCountry(entityCountries);
        return "Updated successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteCountry(@PathVariable("id") Long id){
        servicePhone.deleteDataAboutCountry(id);
        return "Deleted successfully" + id;
    }
}