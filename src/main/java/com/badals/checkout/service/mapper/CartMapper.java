package com.badals.checkout.service.mapper;

import com.badals.checkout.domain.Cart;
import com.badals.checkout.domain.TenantCart;
import com.badals.checkout.service.dto.CartDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = {})
public interface CartMapper extends EntityMapper<CartDTO, Cart> {

    CartDTO toDto(Cart cart);
    CartDTO toProfileDto(TenantCart cart);

    Cart toEntity(CartDTO cartDTO);

    default Cart fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setId(id);
        return cart;
    }
}

