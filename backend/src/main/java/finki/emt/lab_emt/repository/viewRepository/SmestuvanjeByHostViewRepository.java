package finki.emt.lab_emt.repository.viewRepository;

import finki.emt.lab_emt.model.views.SmestuvanjeByHostView;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmestuvanjeByHostViewRepository extends JpaRepository<SmestuvanjeByHostView,Long> {

    Optional<SmestuvanjeByHostView> findByHostId(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = " refresh materialized view public.smestuvanje_stats_by_host ",nativeQuery = true)
    void updateMaterializedView();
}
