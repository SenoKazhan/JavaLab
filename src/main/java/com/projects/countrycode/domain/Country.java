package com.projects.countrycode.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Countries")
public class EntityCountries {
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

    public EntityCountries() {
    }

    public EntityCountries(Integer id, String countryName, String countryCode, Long phoneCode) {
        this.id = id;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.phoneCode = phoneCode;
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

}
