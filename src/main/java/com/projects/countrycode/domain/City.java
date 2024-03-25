package com.projects.countrycode.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_sequence")
    @SequenceGenerator(name = "city_sequence", sequenceName = "city_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "City_name")
    private String cityName;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Country_id", nullable = false)
    @JsonIgnore
    private Country country;
//you are specifying that there is a foreign key column in the table of the entity
// where the relationship is defined (the "many" side) that references the primary key of the entity on the "one" side

    public City() {}

    public City(String cityName, Country country) {
        this.cityName = cityName;
        this.country = country;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Country getCountry() {
        return country;
    }

    public Long getId() {
        return id;
    }

    public void setCountry(Country country) {
        this.country = country;
    }


}
