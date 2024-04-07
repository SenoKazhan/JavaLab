package com.projects.countrycode.dto;

import java.util.List;
import lombok.Data;

/** The type Country dto. */
@Data
public class CountryDto {
  private Integer id;
  private String name;
  private String code;
  private Long phone;
  private List<String> languages;

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
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
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
   * @param code the code
   */
  public void setCode(String code) {
    this.code = code;
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
   * @param phone the phone
   */
  public void setPhone(Long phone) {
    this.phone = phone;
  }

  /**
   * Gets languages.
   *
   * @return the languages
   */
  public List<String> getLanguages() {
    return languages;
  }

  /**
   * Sets languages.
   *
   * @param languages the languages
   */
  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }
}
