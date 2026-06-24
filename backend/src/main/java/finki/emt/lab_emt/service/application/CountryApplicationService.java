package finki.emt.lab_emt.service.application;

import finki.emt.lab_emt.model.dto.countryDTO.CreateCountryDTO;
import finki.emt.lab_emt.model.dto.countryDTO.DisplayCountryDTO;

import java.util.List;
import java.util.Optional;

public interface CountryApplicationService {

    List<DisplayCountryDTO> findAll();

    Optional<DisplayCountryDTO> findById(Long id);

    void deleteById(Long id);

    Optional<DisplayCountryDTO> save(CreateCountryDTO Country);

    Optional<DisplayCountryDTO> update(Long id, CreateCountryDTO country);
}
