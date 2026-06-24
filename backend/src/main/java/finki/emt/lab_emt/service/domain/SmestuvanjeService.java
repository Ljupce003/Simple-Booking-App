package finki.emt.lab_emt.service.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import finki.emt.lab_emt.model.domain.AgencyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import finki.emt.lab_emt.model.domain.Smestuvanje;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.SmestuvanjeStatsDTO;
import finki.emt.lab_emt.model.enums.CategorySmestuvanje;
import finki.emt.lab_emt.model.views.SmestuvanjeByHostView;

public interface SmestuvanjeService {

    List<Smestuvanje> findAll();
    Page<Smestuvanje> findAll(Pageable pageable);

    Optional<Smestuvanje> findById(Long id);

    void deleteById(Long id);

    Optional<Smestuvanje> save(Smestuvanje smestuvanje);

    List<Smestuvanje> filter(Smestuvanje smestuvanje);

    Optional<Smestuvanje> update(Long id, Smestuvanje smestuvanje);


//    Optional<Smestuvanje> addReservation(Long id, Reservation reservation);

    Optional<Smestuvanje> rent(Long id,String user);
    List<Smestuvanje> findRented(String username);
    Optional<Smestuvanje> rentOut(Long id, String remoteUser);

    HashMap<CategorySmestuvanje,Double> statistics();
    List<SmestuvanjeStatsDTO> stats();


    List<SmestuvanjeByHostView> getViewAll();
    Optional<SmestuvanjeByHostView> getViewByHost(Long host);
    void updateView();

    List<Smestuvanje> fetch_w_relations();
    Optional<Smestuvanje> fetch_w_relations_by_id(Long id);

    Optional<Boolean> addReservation(Smestuvanje smestuvanje, AgencyUser user);
}
