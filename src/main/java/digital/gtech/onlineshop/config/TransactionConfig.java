package digital.gtech.onlineshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionConfig {

    private final BigDecimal TAX_PERCENTAGE;
    private final BigDecimal SHIPPING_FEE;

    public TransactionConfig(
            @Value("${digital.gtech.online-shop.tax-percentage}") Double taxPercentage,
            @Value("${digital.gtech.online-shop.shipping-fee}") Double shippingFee
    ) {
        TAX_PERCENTAGE = BigDecimal.valueOf(taxPercentage);
        SHIPPING_FEE = BigDecimal.valueOf(shippingFee);
    }

    public BigDecimal getTaxPercentage() {
        return TAX_PERCENTAGE;
    }

    public BigDecimal getShippingFee() {
        return SHIPPING_FEE;
    }
}
