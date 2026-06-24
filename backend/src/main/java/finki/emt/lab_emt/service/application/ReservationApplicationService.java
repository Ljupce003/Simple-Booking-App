package finki.emt.lab_emt.service.application;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.dto.reservationDTO.CreateReservationDTO;
import finki.emt.lab_emt.model.dto.reservationDTO.DisplayReservationDTO;
import finki.emt.lab_emt.model.dto.reservationDTO.DisplayReservationDTOwC;
import finki.emt.lab_emt.model.exceptions.SmestuvanjeOccupiedException;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

public interface ReservationApplicationService {
    List<DisplayReservationDTO> findAll();

    Optional<DisplayReservationDTO> findById(Long id);

    void deleteById(Long id);

    Optional<DisplayReservationDTO> update(Long id, CreateReservationDTO reservation);

    List<DisplayReservationDTOwC> getUserReservations(String username);

}
