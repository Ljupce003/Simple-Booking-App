package finki.emt.lab_emt.service.domain;

import java.util.List;
import java.util.Optional;

import finki.emt.lab_emt.model.domain.Country;

public interface CountryService {

    List<Country> findAll();

    Optional<Country> findById(Long id);

    void deleteById(Long id);

    Optional<Country> save(Country Country);

    Optional<Country> update(Long id, Country country);
}
