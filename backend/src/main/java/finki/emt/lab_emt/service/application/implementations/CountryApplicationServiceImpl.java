package finki.emt.lab_emt.service.application.implementations;

import finki.emt.lab_emt.model.dto.countryDTO.CreateCountryDTO;
import finki.emt.lab_emt.model.dto.countryDTO.DisplayCountryDTO;
import finki.emt.lab_emt.service.application.CountryApplicationService;
import finki.emt.lab_emt.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryApplicationServiceImpl implements CountryApplicationService {

    private final CountryService countryService;

    public CountryApplicationServiceImpl(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public List<DisplayCountryDTO> findAll() {
        return DisplayCountryDTO.toDTO(this.countryService.findAll());
    }

    @Override
    public Optional<DisplayCountryDTO> findById(Long id) {
        return this.countryService.findById(id)
                .map(DisplayCountryDTO::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        this.countryService.deleteById(id);
    }

    @Override
    public Optional<DisplayCountryDTO> save(CreateCountryDTO countryDTO) {


        return this.countryService.save(countryDTO.toCountry())
                .map(DisplayCountryDTO::toDTO);

    }

    @Override
    public Optional<DisplayCountryDTO> update(Long id, CreateCountryDTO country) {

        return this.countryService.update(id,country.toCountry())
                .map(DisplayCountryDTO::toDTO);
    }
}
