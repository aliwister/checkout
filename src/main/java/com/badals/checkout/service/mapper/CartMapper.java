package com.badals.checkout.service.mapper;

import com.badals.checkout.domain.Checkout;
import com.badals.checkout.service.dto.CartDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = {AdjustmentProfileMapper.class})
public interface CartMapper extends EntityMapper<CartDTO, Checkout> {


   CartDTO toDto(Checkout cart);

   Checkout toEntity(CartDTO cartDTO);

   default Checkout fromId(Long id) {
      if (id == null) {
         return null;
      }
      Checkout cart = new Checkout();
      cart.setId(id);
      return cart;
   }
}