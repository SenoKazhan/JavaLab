package com.projects.countrycode.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/** The type Language. */
@Entity
@Table(name = "languages")
public class Language {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lang_sequence")
  @SequenceGenerator(name = "lang_sequence", sequenceName = "lang_id_seq", allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      mappedBy = "languages")
  private Set<Country> countries = new HashSet<>();

  /** Instantiates a new Language. */
  public Language() {}

  /**
   * Instantiates a new Language.
   *
   * @param languageName the language name
   * @param countries the countries
   */
  public Language(String languageName, Set<Country> countries) {
    this.name = languageName;
    this.countries = countries;
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
   * @param languageName the language name
   */
  public void setName(String languageName) {
    this.name = languageName;
  }

  /**
   * Gets countries.
   *
   * @return the countries
   */
  public Set<Country> getCountries() {
    return countries;
  }

  /**
   * Sets countries.
   *
   * @param countries the countries
   */
  public void setCountries(Set<Country> countries) {
    this.countries = countries;
  }

  /**
   * Add country.
   *
   * @param country the country
   */
  public void addCountry(Country country) {
    this.countries.add(country);
    country.getLanguages().add(this);
  }

  /**
   * Remove country.
   *
   * @param tagId the tag id
   */
  public void removeCountry(long tagId) {
    Country tag = this.countries.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
    if (tag != null) {
      this.countries.remove(tag);
      tag.getLanguages().remove(this);
    }
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
