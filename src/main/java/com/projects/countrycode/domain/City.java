package com.projects.countrycode.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class City {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_sequence")
  @SequenceGenerator(name = "city_sequence", sequenceName = "city_id_seq", allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "country_id", nullable = false)
  @JsonIgnore
  private Country country;

  // you are specifying that there is a foreign key column in the table of the entity
  // where the relationship is defined (the "many" side) that references the primary key of the
  // entity on the "one" side

  public City() {}

  public City(String name, Country country) {
    this.name = name;
    this.country = country;
  }

  public String getName() {
    return name;
  }

  public void setName(String cityName) {
    this.name = cityName;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public Long getId() {
    return id;
  }
}
