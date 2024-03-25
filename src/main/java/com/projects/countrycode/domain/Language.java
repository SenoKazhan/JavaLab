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
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "languages")
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
    public void addCountry(Country country) {
        this.countries.add(country);
        country.getLanguages().add(this);
    }
    public void removeTag(long tagId) {
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
