package digital.gtech.onlineshop.repository;

import digital.gtech.onlineshop.entity.MarginPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarginPeriodRepository extends JpaRepository<MarginPeriod, String> {
    @Query("SELECT m FROM MarginPeriod m WHERE " +
            "(m.startDate <= :endDate AND m.endDate >= :startDate)")
    List<MarginPeriod> findOverlap(LocalDate startDate, LocalDate endDate);

    @Query("SELECT m FROM MarginPeriod m WHERE " +
            "m.startDate <= :today AND m.endDate >= :today " +
            "AND m.isActive = true")
    Optional<MarginPeriod> findActiveMargin(LocalDate today);
}
