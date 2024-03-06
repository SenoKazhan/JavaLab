package com.projects.countrycode.controller;
//Controller: Отвечает за обработку запросов от клиента и взаимодействие с пользовательским интерфейсом.

import com.projects.countrycode.domain.EntityCountries;
import com.projects.countrycode.service.ServicePhone;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries") //Для категории сайта. Без неё подкатегории НЕ РАБОТАЮТ.
public class PhoneController{
    private final ServicePhone servicePhone;

    public PhoneController(ServicePhone servicePhone) {
        this.servicePhone = servicePhone;
    }

    @GetMapping
    public List<EntityCountries> findAllCountries(){
        return servicePhone.findAllCountries();
    }

    @GetMapping("/getCountryInfo")
    public String getCountryInfo(@RequestParam(name = "name", defaultValue = "null") String countryName) {
        List<EntityCountries> countries = servicePhone.findByName(countryName);
        // Возвращает ВСЕ соответсвующее сущности, в то время как Optional либо одну (уникальный), либо ничего.
        if (countries.isEmpty()) {
            return "No country found for the name: " + countryName;
        }
        EntityCountries country = countries.get(0); // Берём первую страну, так как List допускает наличие нескольких сущностей.
        String countryCode = country.getCountryCode();
        Long phoneCode = country.getPhoneCode();

        return "Country code and phone code for " + countryName + ": " + countryCode + ", " + phoneCode;
    }


    @GetMapping("/{id}")
    public Optional<EntityCountries> findCountryById(@PathVariable("id") Long id){
        return servicePhone.findById(id);
    }

    @PostMapping
    public String saveCountry(@RequestBody EntityCountries entityCountries){
       servicePhone.saveCountry(entityCountries);
       return "Saved successfully";
    }

    @PutMapping
    public String updateCountry(@RequestBody EntityCountries entityCountries){
        servicePhone.updateCountry(entityCountries);
        return "Updated successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteCountry(@PathVariable("id") Long id){
        servicePhone.deleteDataAboutCountry(id);
        return "Deleted successfully" + id;
    }
}