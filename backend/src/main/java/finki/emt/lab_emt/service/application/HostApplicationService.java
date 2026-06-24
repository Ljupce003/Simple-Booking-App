package finki.emt.lab_emt.service.application;

import finki.emt.lab_emt.model.dto.hostDTO.CreateHostDTO;
import finki.emt.lab_emt.model.dto.hostDTO.DisplayHostDTO;
import finki.emt.lab_emt.model.views.HostByCountry;

import java.util.List;
import java.util.Optional;

public interface HostApplicationService {
    List<DisplayHostDTO> findAll();

    Optional<DisplayHostDTO> findById(Long id);

    void deleteById(Long id);

    Optional<DisplayHostDTO> save(CreateHostDTO host);

    Optional<DisplayHostDTO> update(Long id, CreateHostDTO host);

    List<HostByCountry> findAllViews();

    Optional<HostByCountry> findViewByCountry(Long id);

}
