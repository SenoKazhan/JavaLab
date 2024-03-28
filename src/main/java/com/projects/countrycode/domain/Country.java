package com.projects.countrycode.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// one-to-many relationship (1 department employs many employees)
// many-to-one (many employees work in one department).

@Entity
@Table(name = "countries")
public class Country {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @Column(name = "phone")
  private Long phone;

  // @
  @OneToMany(
      mappedBy = "country",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  // Аннотация mappedBy используется в двухсторонней связи, отношение управляется City.
  // CascadeType определяет, что должно происходить с зависимыми сущностями, если мы меняем главную
  // сущность.
  // All означает, что все действия, которые мы выполняем с родительским объектом, нужно повторить и
  // для его зависимых объектов.
  private List<City> cities = new ArrayList<>();

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "count_lang",
      joinColumns =
          @JoinColumn(
              name = "country_id"), // поле или столбец, который будет использоваться для внешнего
      // ключа.
      inverseJoinColumns = @JoinColumn(name = "language_id"))
  @JsonIgnore
  private Set<Language> languages = new HashSet<>();

  // @
  public Country() {}

  public Country(Integer id, String countryName, String countryCode, Long phoneCode) {
    this.id = id;
    this.name = countryName;
    this.code = countryCode;
    this.phone = phoneCode;
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

  public String getName() {
    return name;
  }

  public void setName(String countryName) {
    this.name = countryName;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String countryCode) {
    this.code = countryCode;
  }

  public Long getPhone() {
    return phone;
  }

  public void setPhone(Long phoneCode) {
    this.phone = phoneCode;
  }

  public void addCity(City city) {
    cities.add(city);
    city.setCountry(this);
  }

  public void addLanguage(Language language) {
    if (languages == null) {
      languages = new HashSet<>();
    }
    languages.add(language);
    language.getCountries().add(this);
  }

  public Set<Language> getLanguages() {
    return languages;
  }

  public void setLanguages(Set<Language> languages) {
    this.languages = languages;
  }
}
