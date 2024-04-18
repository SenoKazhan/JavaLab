package com.projects.countrycode.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.projects.countrycode.component.Cache;
import com.projects.countrycode.domain.Language;
import com.projects.countrycode.repodao.CityRepository;
import com.projects.countrycode.repodao.CountryRepository;
import com.projects.countrycode.repodao.LanguageRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

class LanguageServiceTest {

  private static final String CACHE_KEY = "lang-";

  @Mock
  private LanguageRepository languageRepository;

  @Mock
  private Cache cache;

  @Mock
  private LanguageServiceImp languageService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    languageRepository = mock(LanguageRepository.class);
    cache = mock(Cache.class);
    languageService = new LanguageServiceImp(languageRepository, cache);
  }


  @Test
  void getLanguageById_ShouldReturnLanguage_WhenLanguageFoundInCache() {
    // Arrange
    Integer languageId = 1;
    Cache cache = mock(Cache.class);
    LanguageRepository languageRepository = mock(LanguageRepository.class);
    Language expectedLanguage = new Language("Test Language");
    when(cache.containsKey(CACHE_KEY + languageId)).thenReturn(true);
    when(cache.getCache(CACHE_KEY + languageId)).thenReturn(expectedLanguage);
    LanguageServiceImp languageService = new LanguageServiceImp(languageRepository, cache);

    // Act
    Language result = languageService.getLanguageById(languageId);

    // Assert
    assertEquals(expectedLanguage, result);
  }

  @Test
  void getLanguageById_ShouldReturnLanguage_WhenLanguageFoundInRepository() {
    // Arrange
    Integer languageId = 1;
    Cache cache = mock(Cache.class);
    LanguageRepository languageRepository = mock(LanguageRepository.class);
    Language expectedLanguage = new Language("Test Language");
    when(cache.containsKey(CACHE_KEY + languageId)).thenReturn(false);
    when(languageRepository.findById(languageId)).thenReturn(Optional.of(expectedLanguage));
    LanguageServiceImp languageService = new LanguageServiceImp(languageRepository, cache);

    // Act
    Language result = languageService.getLanguageById(languageId);

    // Assert
    assertEquals(expectedLanguage, result);
    verify(cache, times(1)).putCache(CACHE_KEY + languageId, expectedLanguage);
  }

  @Test
  void getLanguageById_ShouldThrowNotFoundStatusException_WhenLanguageNotFound() {
    // Arrange
    Integer languageId = 1;
    when(cache.containsKey("lang-" + languageId)).thenReturn(false);
    when(languageRepository.findById(languageId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ResponseStatusException.class, () -> languageService.getLanguageById(languageId));
  }


  @Test
  void getAllLanguages_ShouldReturnListOfLanguages() {
    // Arrange
    List<Language> expectedLanguages = new ArrayList<>();
    expectedLanguages.add(new Language("English"));
    expectedLanguages.add(new Language("French"));
    Cache cache = mock(Cache.class);
    LanguageRepository languageRepository = mock(LanguageRepository.class);
    when(languageRepository.findAll()).thenReturn(expectedLanguages);
    LanguageServiceImp languageService = new LanguageServiceImp(languageRepository, cache);

    // Act
    List<Language> result = languageService.getAllLanguages();

    // Assert
    assertEquals(expectedLanguages, result);
  }

  @Test
  void save_ShouldSaveLanguage() {
    // Arrange
    Language languageToSave = new Language();
    Cache cache = mock(Cache.class);
    LanguageRepository languageRepository = mock(LanguageRepository.class);
    LanguageServiceImp languageService = new LanguageServiceImp(languageRepository, cache);

    // Act
    languageService.save(languageToSave);

    // Assert
    verify(languageRepository, times(1)).save(languageToSave);
  }

  @Test
  void update_ShouldUpdateLanguageAndPutInCache() {
    // Arrange
    Integer languageId = 1;
    Language updatedLanguage = new Language("Updated Language");
    Cache cache = mock(Cache.class);
    LanguageRepository languageRepository = mock(LanguageRepository.class);
    Language existingLanguage = new Language("Existing Language");
    when(languageRepository.findById(languageId)).thenReturn(Optional.of(existingLanguage));
    LanguageServiceImp languageService = new LanguageServiceImp(languageRepository, cache);

    // Act
    languageService.update(updatedLanguage, languageId);

    // Assert
    assertEquals("Updated Language", existingLanguage.getName());
    verify(languageRepository, times(1)).save(existingLanguage);
    verify(cache, times(1)).putCache(CACHE_KEY + languageId, existingLanguage);
  }
  @Test
  void update_ShouldGetLanguageFromCacheAndUpdate() {
    // Arrange
    Integer languageId = 1;
    Language updatedLanguage = new Language("Updated Language");
    Cache cache = mock(Cache.class);
    LanguageRepository languageRepository = mock(LanguageRepository.class);
    Language existingLanguage = new Language("Existing Language");
    when(cache.containsKey(CACHE_KEY + languageId)).thenReturn(true);
    when(cache.getCache(CACHE_KEY + languageId)).thenReturn(existingLanguage);
    when(languageRepository.findById(languageId)).thenReturn(Optional.of(existingLanguage));
    LanguageServiceImp languageService = new LanguageServiceImp(languageRepository, cache);

    // Act
    languageService.update(updatedLanguage, languageId);

    // Assert
    assertEquals("Updated Language", existingLanguage.getName());
    verify(languageRepository, times(1)).save(existingLanguage);
    verify(cache, times(1)).putCache(CACHE_KEY + languageId, existingLanguage);
  }


  @Test
  void deleteLanguage_ShouldDeleteLanguageAndRemoveFromCache() {
    // Arrange
    Integer languageId = 1;
    Cache cache = mock(Cache.class);
    LanguageRepository languageRepository = mock(LanguageRepository.class);
    LanguageServiceImp languageService = new LanguageServiceImp(languageRepository, cache);

    // Act
    languageService.deleteLanguage(languageId);

    // Assert
    verify(languageRepository, times(1)).deleteById(languageId);
    verify(cache, times(1)).remove(CACHE_KEY + languageId);
  }
}
