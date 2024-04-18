package com.projects.countrycode.service;

import com.projects.countrycode.component.Cache;
import com.projects.countrycode.domain.Country;
import com.projects.countrycode.repodao.CountryRepository;
import com.projects.countrycode.repodao.LanguageRepository;
import com.projects.countrycode.service.PhoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PhoneServiceTest {
    @Mock
    private CountryRepository countryRepository;

    @Mock
    private Cache cache;

    @Mock private PhoneServiceImp phoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        countryRepository = mock(CountryRepository.class);
        cache = mock(Cache.class);
        phoneService = new PhoneServiceImp(countryRepository, cache);
    }

    @Test
    void testFindById_CacheHit() {
        // Mock data
        int countryId = 1;
        Country country = new Country();
        when(cache.getCache("country-" + countryId)).thenReturn(country);

        // Call the service method
        Country result = phoneService.findById(countryId);

        // Verify the cache was accessed
        verify(cache).getCache("country-" + countryId);

        // Verify the repository method was not called
        verify(countryRepository, never()).findById(countryId);

        // Verify the result
        assertSame(country, result);
    }

    @Test
    void testFindById_CacheMiss() {
        // Mock data
        int countryId = 1;
        Country country = new Country();
        when(cache.getCache("country-" + countryId)).thenReturn(null);
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

        // Call the service method
        Country result = phoneService.findById(countryId);

        // Verify the cache was accessed
        verify(cache).getCache("country-" + countryId);

        // Verify the repository method was called
        verify(countryRepository).findById(countryId);

        // Verify the cache was updated
        verify(cache).putCache("country-" + countryId, country);

        // Verify the result
        assertSame(country, result);
    }

    @Test
    void testFindById_NotFound() {
        // Mock data
        int countryId = 1;
        when(cache.getCache("country-" + countryId)).thenReturn(null);
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->  phoneService.findById(countryId));

        // Verify the cache was accessed
        verify(cache).getCache("country-" + countryId);

        // Verify the repository method was called
        verify(countryRepository).findById(countryId);

        // Verify the cache was not updated
        verify(cache, never()).putCache(any(), any());
    }

    @Test
    void testFindAllCountries() {
        // Mock data
        List<Country> countries = new ArrayList<>();
        when(countryRepository.findAll()).thenReturn(countries);

        // Call the service method
        List<Country> result = phoneService.findAllCountries();

        // Verify the repository method was called
        verify(countryRepository).findAll();

        // Verify the result
        assertSame(countries, result);
    }

    @Test
    void testSaveCountry() {
        // Mock data
        Country country = new Country();

        // Call the service method
        phoneService.saveCountry(country);

        // Verify the cache was updated
        verify(cache).putCache("country-" + country.getId(), country);

        // Verify the repository method was called
        verify(countryRepository).save(country);
    }

    @Test
    void testUpdateCountry_CacheHit() {
        // Mock data
        int countryId = 1;
        Country countryData = new Country();
        Country cachedCountry = new Country();
        when(cache.containsKey("country-" + countryId)).thenReturn(true);
        when(cache.getCache("country-" + countryId)).thenReturn(cachedCountry);

        // Call the service method
        phoneService.updateCountry(countryData, countryId);

        // Verify the cache was accessed
        verify(cache).containsKey("country-" + countryId);
        verify(cache).getCache("country-" + countryId);

        // Verify the repository method was not called
        verify(countryRepository, never()).findById(countryId);

        // Verify the country data was updated
        assertSame(countryData.getName(), cachedCountry.getName());

        // Verify the cache was updated
        verify(cache).putCache("country-" + countryId, cachedCountry);

        // Verify the repository method was called
        verify(countryRepository).save(cachedCountry);
    }

    @Test
    void testUpdateCountry_CacheMiss() {
        // Mock data
        int countryId = 1;
        Country countryData = new Country();
        Country country = new Country();
        when(cache.containsKey("country-" + countryId)).thenReturn(false);
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(country));

        // Call the service method
        phoneService.updateCountry(countryData, countryId);

        // Verify the cache was accessed
        verify(cache).containsKey("country-" + countryId);

        // Verify the repository method was called
        verify(countryRepository).findById(countryId);

        // Verify the country data was updated
        assertSame(countryData.getName(), country.getName());

        // Verify the cache was updated
        verify(cache).putCache("country-" + countryId, country);

        // Verify the repository method was called
        verify(countryRepository).save(country);
    }

    @Test
    void testUpdateCountry_NotFound() {
        // Mock data
        int countryId = 1;
        Country countryData = new Country();
        when(cache.containsKey("country-" + countryId)).thenReturn(false);
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());


        assertThrows(ResponseStatusException.class, () -> phoneService.updateCountry(countryData, countryId));

        // Verify the cache was accessed
        verify(cache).containsKey("country-" + countryId);

        // Verify the repository method was called
        verify(countryRepository).findById(countryId);

        // Verify the cache was not updated
        verify(cache, never()).putCache(any(), any());

        // Verify the repository method was not called
        verify(countryRepository, never()).save(any());
    }

    @Test
    void testFindByPhoneCode() {
        // Mock data
        Long phoneCode = 123456L;
        List<Country> countries = new ArrayList<>();
        Country country1 = new Country();
        country1.setPhone(phoneCode);
        Country country2 = new Country();
        country2.setPhone(phoneCode);
        countries.add(country1);
        countries.add(country2);
        when(countryRepository.findAll()).thenReturn(countries);

        // Call the service method
        List<Country> result = phoneService.findByPhoneCode(phoneCode);

        // Verify the repository method was called
        verify(countryRepository).findAll();

        // Verify the result
        assertEquals(2, result.size());
        assertTrue(result.contains(country1));
        assertTrue(result.contains(country2));
    }

    @Test
    void testDeleteCountry() {
        // Mock data
        int countryId = 1;

        // Call the service method
        phoneService.deleteCountry(countryId);

        // Verify the repository method was called
        verify(countryRepository).deleteById(countryId);

        // Verify the cache was updated
        verify(cache).remove("country-" + countryId);
    }

    // Add more tests for other methods in the PhoneServiceImp class
}