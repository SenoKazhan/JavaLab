package com.projects.countrycode.repodao;

import com.projects.countrycode.domain.Language;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface Language repository. */
@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
  /**
   * Find by name optional.
   *
   * @param name the name
   * @return the optional
   */
  Optional<Language> findByName(String name);

  @Transactional
  void deleteById(Integer id);
}
