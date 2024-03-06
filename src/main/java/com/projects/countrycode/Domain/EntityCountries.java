package com.projects.countrycode.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Countries")
public class EntityCountries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Country_name")
    private String countryName;
    @Column(name = "Country_code")
    private String countryCode;
    @Column(name = "Phone_code")
    private Long phoneCode;

    public EntityCountries() {
    }

    public EntityCountries(Long id, String countryName, String countryCode, Long phoneCode) {
        this.id = id;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.phoneCode = phoneCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
