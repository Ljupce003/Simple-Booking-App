package finki.emt.lab_emt.service.application.implementations;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.domain.Reservation;
import finki.emt.lab_emt.model.domain.Smestuvanje;
import finki.emt.lab_emt.model.dto.reservationDTO.CreateReservationDTO;
import finki.emt.lab_emt.model.dto.reservationDTO.DisplayReservationDTO;
import finki.emt.lab_emt.model.dto.reservationDTO.DisplayReservationDTOwC;
import finki.emt.lab_emt.model.exceptions.SmestuvanjeOccupiedException;
import finki.emt.lab_emt.repository.SmestuvanjeRepository;
import finki.emt.lab_emt.service.application.ReservationApplicationService;
import finki.emt.lab_emt.service.domain.ReservationService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationApplicationServiceImpl implements ReservationApplicationService {

    private final ReservationService reservationService;
    private final SmestuvanjeRepository smestuvanjeRepository;

    public ReservationApplicationServiceImpl(ReservationService reservationService,
                                             SmestuvanjeRepository smestuvanjeRepository) {
        this.reservationService = reservationService;
        this.smestuvanjeRepository = smestuvanjeRepository;
    }

    @Override
    public List<DisplayReservationDTO> findAll() {
        return DisplayReservationDTO.toDTO(this.reservationService.findAll());
    }

    @Override
    public Optional<DisplayReservationDTO> findById(Long id) {
        return this.reservationService.findById(id)
                .map(DisplayReservationDTO::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        this.reservationService.deleteById(id);
    }

    @Override
    public Optional<DisplayReservationDTO> update(Long id, CreateReservationDTO reservationDTO) {

        if(reservationDTO == null || reservationDTO.smestuvanjeID()==null) return Optional.empty();

        Optional<Smestuvanje> smestuvanje = this.smestuvanjeRepository.findById(reservationDTO.smestuvanjeID());

        return this.reservationService.update(id,reservationDTO.toReservation(smestuvanje.orElse(null)))
                .map(DisplayReservationDTO::toDTO);
    }

    @Override
    public List<DisplayReservationDTOwC> getUserReservations(String username) {

        if(username == null) return new ArrayList<>();
        List<DisplayReservationDTOwC> out = new ArrayList<>();

        reservationService.getUserConfirmedReservations(username)
                .forEach(res -> out.add(DisplayReservationDTOwC.toDTO(res,true)));

        reservationService.getUserTemporaryReservations(username)
                .forEach(res -> out.add(DisplayReservationDTOwC.toDTO(res,false)));

        return out;


    }

}
