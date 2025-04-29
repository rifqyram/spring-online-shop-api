package digital.gtech.onlineshop.repository;

import digital.gtech.onlineshop.entity.TransactionDetail;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
    @Query("SELECT td FROM TransactionDetail td " +
            "JOIN td.product p " +
            "JOIN p.productType pt " +
            "JOIN td.margin m " +
            "WHERE td.transaction.transactionDate BETWEEN :startDate AND :endDate")
    Page<TransactionDetail> findSalesReportData(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
