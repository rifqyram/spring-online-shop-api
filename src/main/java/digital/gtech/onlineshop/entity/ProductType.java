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

@Entity
@Table(name = TableConstant.PRODUCT_TYPE_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductType extends BaseEntity {

    @Column(name = "type", nullable = false)
    private String type;
}
