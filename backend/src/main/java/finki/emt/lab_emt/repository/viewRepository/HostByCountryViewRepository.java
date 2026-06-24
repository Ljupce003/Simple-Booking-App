package finki.emt.lab_emt.repository.viewRepository;

import finki.emt.lab_emt.model.views.HostByCountry;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface HostByCountryViewRepository extends JpaRepository<HostByCountry,Long> {

    Optional<HostByCountry> findHostByCountryId(Long countryId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "refresh materialized view public.host_by_country",nativeQuery = true)
    void updateMaterializedView();
}
