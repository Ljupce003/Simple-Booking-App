package finki.emt.lab_emt.service.domain.implementations;

import finki.emt.lab_emt.events.HostEvents.HostCreatedEvent;
import finki.emt.lab_emt.events.HostEvents.HostDeletedEvent;
import finki.emt.lab_emt.events.HostEvents.HostUpdatedEvent;
import finki.emt.lab_emt.model.domain.Host;
import finki.emt.lab_emt.model.views.HostByCountry;
import finki.emt.lab_emt.repository.CountryRepository;
import finki.emt.lab_emt.repository.HostRepository;
import finki.emt.lab_emt.repository.viewRepository.HostByCountryViewRepository;
import finki.emt.lab_emt.service.domain.HostService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;
    private final CountryRepository countryRepository;
    private final HostByCountryViewRepository hostByCountryViewRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public HostServiceImpl(HostRepository hostRepository, CountryRepository countryRepository, HostByCountryViewRepository hostByCountryViewRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.hostRepository = hostRepository;
        this.countryRepository = countryRepository;
        this.hostByCountryViewRepository = hostByCountryViewRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Host> findAll() {
        return this.hostRepository.findAll();
    }

    @Override
    public Optional<Host> findById(Long id) {
        return this.hostRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Host> host = findById(id);
        this.hostRepository.deleteById(id);

        if(host.isPresent()){
            this.applicationEventPublisher.publishEvent(new HostDeletedEvent(host.get()));
        }
    }

    @Override
    public Optional<Host> save(Host host) {

        if(host!=null && host.getName()!=null && host.getSurname()!=null && host.getCountry()!=null){
            Optional<Host> optionalHost = Optional.of(this.hostRepository.save( new Host(host.getName(),host.getSurname(),host.getCountry())));
            if(optionalHost.isPresent()){
                this.applicationEventPublisher.publishEvent(new HostCreatedEvent(optionalHost.get()));
                return optionalHost;
            }

        }

        return Optional.empty();

    }

    @Override
    public Optional<Host> update(Long id, Host host) {

        if(host == null){
            return Optional.empty();
        }

        return this.hostRepository.findById(id).map(
                exisitingHost -> {
                    if(host.getName() != null){
                        exisitingHost.setName(host.getName());
                    }
                    if(host.getSurname() !=null){
                        exisitingHost.setSurname(host.getSurname());
                    }
                    if(host.getCountry()!=null && host.getCountry().getId() !=null &&
                            this.countryRepository.findById(host.getCountry().getId()).isPresent()){
                        exisitingHost.setCountry(this.countryRepository.findById(host.getCountry().getId()).get());
                    }

                    Host savedHost = this.hostRepository.save(exisitingHost);

                    this.applicationEventPublisher.publishEvent(new HostUpdatedEvent(savedHost));

                    return savedHost;
                }
        );
    }

    @Override
    public List<HostByCountry> findAllViews() {
        return this.hostByCountryViewRepository.findAll();
    }

    @Override
    public Optional<HostByCountry> findViewByCountry(Long id) {
        return this.hostByCountryViewRepository.findHostByCountryId(id);
    }

    @Override
    public void updateMaterializedView() {
        this.hostByCountryViewRepository.updateMaterializedView();
    }
}
