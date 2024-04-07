package com.projects.countrycode.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// one-to-many relationship (1 department employs many employees)
// many-to-one (many employees work in one department).

/** The type Country. */
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

  /** Instantiates a new Country. */
  // @
  public Country() {}

  /**
   * Instantiates a new Country.
   *
   * @param id the id
   * @param countryName the country name
   * @param countryCode the country code
   * @param phoneCode the phone code
   */
  public Country(Integer id, String countryName, String countryCode, Long phoneCode) {
    this.id = id;
    this.name = countryName;
    this.code = countryCode;
    this.phone = phoneCode;
  }

  /**
   * Sets cities.
   *
   * @param cities the cities
   */
  public void setCities(List<City> cities) {
    this.cities = cities;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Integer id) {
    this.id = id;
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
   * @param countryName the country name
   */
  public void setName(String countryName) {
    this.name = countryName;
  }

  /**
   * Gets code.
   *
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets code.
   *
   * @param countryCode the country code
   */
  public void setCode(String countryCode) {
    this.code = countryCode;
  }

  /**
   * Gets phone.
   *
   * @return the phone
   */
  public Long getPhone() {
    return phone;
  }

  /**
   * Sets phone.
   *
   * @param phoneCode the phone code
   */
  public void setPhone(Long phoneCode) {
    this.phone = phoneCode;
  }

  /**
   * Add city.
   *
   * @param city the city
   */
  public void addCity(City city) {
    cities.add(city);
    city.setCountry(this);
  }

  /**
   * Add language.
   *
   * @param language the language
   */
  public void addLanguage(Language language) {
    if (languages == null) {
      languages = new HashSet<>();
    }
    languages.add(language);
    language.getCountries().add(this);
  }

  /**
   * Gets languages.
   *
   * @return the languages
   */
  public Set<Language> getLanguages() {
    return languages;
  }

  /**
   * Sets languages.
   *
   * @param languages the languages
   */
  public void setLanguages(Set<Language> languages) {
    this.languages = languages;
  }
}
