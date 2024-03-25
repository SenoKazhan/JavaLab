package com.projects.countrycode.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
// one-to-many relationship (1 department employs many employees)
// many-to-one (many employees work in one department).

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Country_name")
    private String countryName;
    @Column(name = "Country_code")
    private String countryCode;
    @Column(name = "Phone_code")
    private Long phoneCode;
    //@
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) //Аннотация mappedBy используется в двухсторонней связи, отношение управляется City.
    //CascadeType определяет, что должно происходить с зависимыми сущностями, если мы меняем главную сущность.
    //All означает, что все действия, которые мы выполняем с родительским объектом, нужно повторить и для его зависимых объектов.
    private List<City> cities =  new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "country_language",
            joinColumns = @JoinColumn(name = "country_id"), //поле или столбец, который будет использоваться для внешнего ключа.
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    @JsonIgnore
    private List<Language> languages =  new ArrayList<>();
    //@
    public Country() {}

    public Country(Integer id, String countryName, String countryCode, Long phoneCode) {
        this.id = id;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.phoneCode = phoneCode;
    }
    public void setCities(List<City> cities) {
        this.cities = cities;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public Long getPhoneCode() {
        return phoneCode;
    }
    public void setPhoneCode(Long phoneCode) {
        this.phoneCode = phoneCode;
    }
    public void addCity(City city){
        cities.add(city);
        city.setCountry(this);
    }
    public List<Language> getLanguages() {
        return languages;
    }
    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
}
