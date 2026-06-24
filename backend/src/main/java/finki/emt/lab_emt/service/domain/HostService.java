package finki.emt.lab_emt.service.domain;

import java.util.List;
import java.util.Optional;

import finki.emt.lab_emt.model.domain.Host;
import finki.emt.lab_emt.model.views.HostByCountry;

public interface HostService {
    List<Host> findAll();

    Optional<Host> findById(Long id);

    void deleteById(Long id);

    Optional<Host> save(Host host);

    Optional<Host> update(Long id, Host host);

    List<HostByCountry> findAllViews();
    Optional<HostByCountry> findViewByCountry(Long id);

    void updateMaterializedView();
}
