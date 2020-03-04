package com.badals.checkout.service;

import com.badals.checkout.domain.Cart;
import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.repository.CartRepository;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.mapper.CartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {
    private final Logger log = LoggerFactory.getLogger(CartService.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartMapper cartMapper;

    @Transactional(readOnly = true)
    public CartDTO findBySecureKey(String secureKey) {
        log.info("Request to get Cart : {}", secureKey);
        log.info("Entity {}", cartRepository.findBySecureKey(secureKey));
        Optional<CartDTO> cartDTO = cartRepository.findBySecureKey(secureKey)
                .map(cartMapper::toDto);
        log.info("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        log.info("Returning DTO {}", cartDTO);
        return cartDTO.get();
    }

    public CartDTO setDeliveryAddress(Address address, String secureKey) {
        Cart cart = cartRepository.findBySecureKey(secureKey).get();
        cart.setDeliveryAddress(address);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartDTO setDeliveryAddressAndEmail(Address address, String email, String secureKey) {
        Cart cart = cartRepository.findBySecureKey(secureKey).get();
        cart.setDeliveryAddress(address);
        cart.setEmail(email);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }
    public CartDTO setCarrier() {
        return null;
    }

}
