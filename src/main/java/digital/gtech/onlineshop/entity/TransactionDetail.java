package digital.gtech.onlineshop.entity;

import digital.gtech.onlineshop.constant.TableConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = TableConstant.TRANSACTION_DETAIL_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TransactionDetail extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "sub_total", nullable = false, precision = 19, scale = 2)
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "margin_id", nullable = false)
    private MarginPeriod margin;

    @Version
    private Integer version;
}
