package com.projects.countrycode.domain;
import jakarta.persistence.*;
import java.util.Set;
@Entity
public class Language {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
    @Column(name = "Language_name")
    private String languageName;
    @ManyToMany(mappedBy = "languages")
    private Set<Country> countries;

    public Language() {}

    public Language(String languageName, Set<Country> countries) {
        this.languageName = languageName;
        this.countries = countries;
    }

    public String getLanguageName() {
        return languageName;
    }
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public Set<Country> getCountries() {
        return countries;
    }
    public void setCountries(Set<Country> countries) {this.countries = countries;}

    public Long getId() {
        return id;
    }
}
