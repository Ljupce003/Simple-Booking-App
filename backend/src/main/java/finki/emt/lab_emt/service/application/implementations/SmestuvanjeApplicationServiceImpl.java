package finki.emt.lab_emt.service.application.implementations;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.DisplaySmestuvanjeWithRelationDTO;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.SmestuvanjeStatsDTO;
import finki.emt.lab_emt.model.enums.CategorySmestuvanje;
import finki.emt.lab_emt.model.domain.Host;

import finki.emt.lab_emt.model.domain.Smestuvanje;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.CreateSmestuvanjeDTO;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.DisplaySmestuvanjeDTO;
import finki.emt.lab_emt.model.views.SmestuvanjeByHostView;
import finki.emt.lab_emt.repository.HostRepository;
import finki.emt.lab_emt.service.application.SmestuvanjeApplicationService;
import finki.emt.lab_emt.service.domain.SmestuvanjeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Service
public class SmestuvanjeApplicationServiceImpl implements SmestuvanjeApplicationService {

    private final SmestuvanjeService smestuvanjeService;
    private final HostRepository hostRepository;

    public SmestuvanjeApplicationServiceImpl(SmestuvanjeService smestuvanjeService, HostRepository hostRepository) {
        this.smestuvanjeService = smestuvanjeService;
        this.hostRepository = hostRepository;
    }


    @Override
    public List<DisplaySmestuvanjeDTO> findAll() {
        return DisplaySmestuvanjeDTO.toDTO(this.smestuvanjeService.findAll());
    }

    @Override
    public Page<DisplaySmestuvanjeDTO> findAll(Pageable pageable) {
        return this.smestuvanjeService.findAll(pageable).map(DisplaySmestuvanjeDTO::toDTO);
    }

    @Override
    public List<DisplaySmestuvanjeDTO> findRented(String username) {
        if (username == null) return null;
        return DisplaySmestuvanjeDTO.toDTO(this.smestuvanjeService.findRented(username));
    }

    @Override
    public Optional<DisplaySmestuvanjeDTO> findById(Long id) {
        return this.smestuvanjeService.findById(id).map(DisplaySmestuvanjeDTO::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        this.smestuvanjeService.deleteById(id);
    }

    @Override
    public Optional<DisplaySmestuvanjeDTO> save(CreateSmestuvanjeDTO smestuvanjeDTO) {

        if(smestuvanjeDTO==null || smestuvanjeDTO.hostID()==null) return Optional.empty();

        Optional<Host> host = this.hostRepository.findById(smestuvanjeDTO.hostID());

        return this.smestuvanjeService.save(smestuvanjeDTO.toSmestuvanje(host.orElse(null)))
                .map(DisplaySmestuvanjeDTO::toDTO);
    }

    @Override
    public List<DisplaySmestuvanjeDTO> filter(CreateSmestuvanjeDTO smestuvanjeDTO) {
        if(smestuvanjeDTO==null) return null;

        Optional<Host> host = Optional.empty();
        if(smestuvanjeDTO.hostID()!=null){
            host = this.hostRepository.findById(smestuvanjeDTO.hostID());
        }
        return DisplaySmestuvanjeDTO.toDTO(this.smestuvanjeService.filter(smestuvanjeDTO.toSmestuvanje(host.orElse(null))));

    }


    @Override
    public Optional<DisplaySmestuvanjeDTO> update(Long id, CreateSmestuvanjeDTO smestuvanjeDTO) {
        if(smestuvanjeDTO==null || id == null) return Optional.empty();

        Optional<Host> host = Optional.empty();
        if(smestuvanjeDTO.hostID()!=null){
            host = this.hostRepository.findById(smestuvanjeDTO.hostID());
        }
        return this.smestuvanjeService.update(id,smestuvanjeDTO.toSmestuvanje(host.orElse(null)))
                .map(DisplaySmestuvanjeDTO::toDTO);
    }

    @Override
    public Optional<Boolean> addReservation(Long id, AgencyUser user) {

        Optional<Smestuvanje> smestuvanje = this.smestuvanjeService.findById(id);

        if(smestuvanje.isEmpty()) return Optional.of(false);

        return this.smestuvanjeService.addReservation(smestuvanje.get(),user);
    }

    @Override
    public Optional<DisplaySmestuvanjeDTO> rent(Long id, String user) {
        return this.smestuvanjeService.rent(id, user).map(DisplaySmestuvanjeDTO::toDTO);
    }

    @Override
    public Optional<DisplaySmestuvanjeDTO> rentOut(Long id, String userName) {

        return this.smestuvanjeService.rentOut(id, userName).map(DisplaySmestuvanjeDTO::toDTO);
    }

    @Override
    public Set<Map.Entry<CategorySmestuvanje, Double>> statistics() {
        return this.smestuvanjeService.statistics().entrySet();
    }

    @Override
    public List<SmestuvanjeStatsDTO> stats() {
        return this.smestuvanjeService.stats();
    }

    @Override
    public List<SmestuvanjeByHostView> getAllViews() {
        return this.smestuvanjeService.getViewAll();
    }

    @Override
    public Optional<SmestuvanjeByHostView> getViewByHost(Long host) {
        return this.smestuvanjeService.getViewByHost(host);
    }

    @Override
    public List<Smestuvanje> fetch_w_relations() {
        return this.smestuvanjeService.fetch_w_relations();
    }

    @Override
    public Optional<DisplaySmestuvanjeWithRelationDTO> fetch_w_relations_by_id(Long id) {
        Optional<Smestuvanje> smestuvanjeOptional = this.smestuvanjeService.fetch_w_relations_by_id(id);
        return smestuvanjeOptional.map(DisplaySmestuvanjeWithRelationDTO::toDTO);
    }


}
