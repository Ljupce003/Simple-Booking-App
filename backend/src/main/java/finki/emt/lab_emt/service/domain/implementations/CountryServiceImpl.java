package finki.emt.lab_emt.service.domain.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import finki.emt.lab_emt.model.domain.Country;
import finki.emt.lab_emt.repository.CountryRepository;
import finki.emt.lab_emt.service.domain.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;

    }

    @Override
    public List<Country> findAll() {
        return this.countryRepository.findAll();
    }

    @Override
    public Optional<Country> findById(Long id) {
        return this.countryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.countryRepository.deleteById(id);
    }

    @Override
    public Optional<Country> save(Country country) {

        if (country == null){
            return Optional.empty();
        }

        return Optional.of(this.countryRepository.save(new Country(country.getName(), country.getContinent())));




    }

    @Override
    public Optional<Country> update(Long id, Country country) {

        if(country == null){
            return Optional.empty();
        }

        return this.countryRepository.findById(id).map(
                existingCountry -> {

                    if(country.getName() !=null){
                        existingCountry.setName(country.getName());
                    }
                    if(country.getContinent() !=null){
                        existingCountry.setContinent(country.getContinent());
                    }

                    return this.countryRepository.save(existingCountry);
                }
        );
    }
}
