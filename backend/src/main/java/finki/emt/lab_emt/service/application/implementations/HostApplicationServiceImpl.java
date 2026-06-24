package finki.emt.lab_emt.service.application.implementations;

import finki.emt.lab_emt.model.domain.Country;
import finki.emt.lab_emt.model.dto.hostDTO.CreateHostDTO;
import finki.emt.lab_emt.model.dto.hostDTO.DisplayHostDTO;
import finki.emt.lab_emt.model.views.HostByCountry;
import finki.emt.lab_emt.repository.CountryRepository;
import finki.emt.lab_emt.service.application.HostApplicationService;
import finki.emt.lab_emt.service.domain.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostApplicationServiceImpl implements HostApplicationService {

    private final HostService hostService;

    private final CountryRepository countryRepository;

    public HostApplicationServiceImpl(HostService hostService, CountryRepository countryRepository) {
        this.hostService = hostService;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<DisplayHostDTO> findAll() {
        return DisplayHostDTO.toDTO(this.hostService.findAll());
    }

    @Override
    public Optional<DisplayHostDTO> findById(Long id) {
        return this.hostService.findById(id).map(DisplayHostDTO::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        this.hostService.deleteById(id);
    }

    @Override
    public Optional<DisplayHostDTO> save(CreateHostDTO hostDTO) {

        if(hostDTO==null) return Optional.empty();

        Optional<Country> country = this.countryRepository.findById(hostDTO.countryID());

        return this.hostService.save(hostDTO.toHost(country.orElse(null))).map(DisplayHostDTO::toDTO);
    }

    @Override
    public Optional<DisplayHostDTO> update(Long id, CreateHostDTO hostDTO) {

        if(hostDTO==null) return Optional.empty();

        Optional<Country> country = this.countryRepository.findById(hostDTO.countryID());

        return this.hostService.update(id,hostDTO.toHost(country.orElse(null))).map(DisplayHostDTO::toDTO);
    }

    @Override
    public List<HostByCountry> findAllViews() {
        return this.hostService.findAllViews();
    }

    @Override
    public Optional<HostByCountry> findViewByCountry(Long id) {
        return this.hostService.findViewByCountry(id);
    }
}
