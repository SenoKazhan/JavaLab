package com.projects.countrycode.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

  public Language() {}

  public Language(String languageName, Set<Country> countries) {
    this.name = languageName;
    this.countries = countries;
  }

  public String getName() {
    return name;
  }

  public void setName(String languageName) {
    this.name = languageName;
  }

  public Set<Country> getCountries() {
    return countries;
  }

  public void setCountries(Set<Country> countries) {
    this.countries = countries;
  }

  public void addCountry(Country country) {
    this.countries.add(country);
    country.getLanguages().add(this);
  }

  public void removeCountry(long tagId) {
    Country tag = this.countries.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
    if (tag != null) {
      this.countries.remove(tag);
      tag.getLanguages().remove(this);
    }
  }

  public Long getId() {
    return id;
  }
}
