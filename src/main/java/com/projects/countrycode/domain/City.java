package com.projects.countrycode.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/** The type City. */
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

  /** Instantiates a new City. */
  public City() {}

  /**
   * Instantiates a new City.
   *
   * @param name the name
   * @param country the country
   */
  public City(String name, Country country) {
    this.name = name;
    this.country = country;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param cityName the city name
   */
  public void setName(String cityName) {
    this.name = cityName;
  }

  /**
   * Gets country.
   *
   * @return the country
   */
  public Country getCountry() {
    return country;
  }

  /**
   * Sets country.
   *
   * @param country the country
   */
  public void setCountry(Country country) {
    this.country = country;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }
}
