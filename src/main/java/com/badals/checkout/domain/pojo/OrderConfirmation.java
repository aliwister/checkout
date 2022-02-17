package com.badals.checkout.domain.pojo;


import com.badals.checkout.service.dto.CartDTO;
import com.badals.enumeration.OrderState;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A DTO for the {@link com.badals.checkout.domain.Order} entity.
 */
@Data
public class OrderConfirmation implements Serializable {

    private Long id;

    private String reference;
    private String confirmationKey;

    private LocalDate invoiceDate;

    private LocalDate deliveryDate;

    private OrderState state;

    private String currency;

    private CartDTO cart;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderConfirmation orderDTO = (OrderConfirmation) o;
        if (orderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", state='" + getState() + "'" +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
