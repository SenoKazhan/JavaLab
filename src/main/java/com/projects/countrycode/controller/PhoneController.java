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
    public String getCountryInfo(@RequestParam(name = "name", defaultValue = "null") String countryName,
                                 @RequestParam(name = "phoneCode", defaultValue = "0") Long phoneCode) {
        if (!countryName.equals("null")) {
            EntityCountries country = servicePhone.findByName(countryName);
            String countryCode = country.getCountryCode();
            Long numberCode = country.getPhoneCode();
            return "Information for country name:\n" +
                    "Country name: "  + countryName +
                    "\nPhone number code: " + numberCode +
                    "\nCountry code: " + countryCode;
        }
        else if (phoneCode != 0) {
            List<EntityCountries> countries = servicePhone.findByPhoneCode(phoneCode);
            if (countries.isEmpty()) {
                return "No countries found for the phone code: " + phoneCode;
            }
            StringBuilder result = new StringBuilder(); // Класс для создания изменяемой последовательности символов.

            EntityCountries firstCountry = countries.get(0);
            result.append("Country info for phone code ").append(phoneCode).append(":\n")
                    .append("Country Name: ").append(firstCountry.getCountryName()).append("\n")
                    .append("Country Code: ").append(firstCountry.getCountryCode()).append("\n")
                    .append("Phone Code: ").append(firstCountry.getPhoneCode()).append("\n\n");
            result.append("Other countries with the same phone code:\n");
            for (int i = 1; i < countries.size(); i++) {
                EntityCountries country = countries.get(i);
                result.append("Country Name: ").append(country.getCountryName()).append("\n")
                        .append("Country Code: ").append(country.getCountryCode()).append("\n")
                        .append("Phone Code: ").append(country.getPhoneCode()).append("\n\n");
            }
            return result.toString();
        }
        else {
            return "No parameters provided";
        }
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