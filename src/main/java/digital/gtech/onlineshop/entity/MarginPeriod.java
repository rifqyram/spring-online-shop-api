package digital.gtech.onlineshop.entity;

import digital.gtech.onlineshop.constant.TableConstant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = TableConstant.MARGIN_PERIOD_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MarginPeriod extends BaseEntity {

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "margin_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal marginPercentage;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
