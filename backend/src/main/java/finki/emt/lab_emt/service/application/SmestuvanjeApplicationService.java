package finki.emt.lab_emt.service.application;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import finki.emt.lab_emt.model.domain.AgencyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import finki.emt.lab_emt.model.domain.Smestuvanje;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.CreateSmestuvanjeDTO;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.DisplaySmestuvanjeDTO;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.DisplaySmestuvanjeWithRelationDTO;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.SmestuvanjeStatsDTO;
import finki.emt.lab_emt.model.enums.CategorySmestuvanje;
import finki.emt.lab_emt.model.views.SmestuvanjeByHostView;

public interface SmestuvanjeApplicationService {
    List<DisplaySmestuvanjeDTO> findAll();

    Page<DisplaySmestuvanjeDTO> findAll(Pageable pageable);
    List<DisplaySmestuvanjeDTO> findRented(String username);

    Optional<DisplaySmestuvanjeDTO> findById(Long id);

    void deleteById(Long id);

    Optional<DisplaySmestuvanjeDTO> save(CreateSmestuvanjeDTO smestuvanje);

    List<DisplaySmestuvanjeDTO> filter(CreateSmestuvanjeDTO smestuvanje);

    Optional<DisplaySmestuvanjeDTO> update(Long id, CreateSmestuvanjeDTO smestuvanjeDto);

    Optional<DisplaySmestuvanjeDTO> rent(Long id,String user);

    Optional<DisplaySmestuvanjeDTO> rentOut(Long id, String remoteUser);

    Set<Map.Entry<CategorySmestuvanje,Double>> statistics();
    List<SmestuvanjeStatsDTO> stats();

    List<SmestuvanjeByHostView> getAllViews();
    Optional<SmestuvanjeByHostView> getViewByHost(Long Host);

    public List<Smestuvanje> fetch_w_relations();


    public Optional<DisplaySmestuvanjeWithRelationDTO> fetch_w_relations_by_id(Long id);


    Optional<Boolean> addReservation(Long id, AgencyUser user);
}
