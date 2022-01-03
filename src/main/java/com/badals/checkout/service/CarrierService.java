package com.badals.checkout.service;

import com.badals.checkout.domain.Cart;
import com.badals.checkout.domain.TenantCart;
import com.badals.checkout.domain.pojo.Carrier;
import com.badals.checkout.repository.CartRepository;
import com.badals.checkout.repository.TenantCartRepository;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.mapper.CartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

@Service
public class CarrierService {
    private final Logger log = LoggerFactory.getLogger(CarrierService.class);

    private final CartRepository cartRepository;
    private final TenantCartRepository tenantCartRepository;

    private final CartMapper cartMapper;

    List<Carrier> ship = new ArrayList<Carrier>(){{
        add(new Carrier("Badals.com Home Delivery -- Muscat Only", "BADALSMUSCAT", BigDecimal.valueOf(1), ""));
        //add(new Carrier("Pickup from our showroom", "PICKUP", BigDecimal.ZERO, ""));
        add(new Carrier("Dakhiliya Home Delivery", "Dakhiliya", BigDecimal.valueOf(2),""));
        add(new Carrier("Batina Home Delivery", "Batina", BigDecimal.valueOf(2),""));
        add(new Carrier("Sharqiya Home Delivery", "Sharqiya", BigDecimal.valueOf(2),""));
        add(new Carrier("Wusta Home Delivery", "Wusta", BigDecimal.valueOf(2.5),""));
        add(new Carrier("Dhahira Home Delivery", "Dhahira", BigDecimal.valueOf(2),""));
        add(new Carrier("Buraymi Home Delivery", "BURAYMI", BigDecimal.valueOf(2),""));
        add(new Carrier("DHL Home Delivery (Salalah/Sohar ONLY)", "DHL", BigDecimal.valueOf(3),""));
    }};
    List<Carrier> pickup = new ArrayList<Carrier>(){{
        add(new Carrier("Store Pickup", "PICKUP", BigDecimal.valueOf(0), ""));
    }};

    public CarrierService(CartRepository cartRepository, TenantCartRepository tenantCartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.tenantCartRepository = tenantCartRepository;
        this.cartMapper = cartMapper;
    }

    @Transactional(readOnly = true)
    public List<Carrier> findByStateCityWeight(String state, String city, BigDecimal weight, boolean isPickup ) {
        if(isPickup) {
            return pickup;
        }
        return ship;
    }

    public CartDTO setCarrier(String value, String secureKey) throws InvalidCartException {
        Cart cart = cartRepository.findBySecureKey(secureKey).orElse(null);
        if(cart == null)
            throw new InvalidCartException("cart invalid");
        cart.setCarrier(value);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartDTO setTenantCarrier(String value, String secureKey) throws InvalidCartException {
        TenantCart cart = tenantCartRepository.findBySecureKey(secureKey).orElse(null);
        if(cart == null)
            throw new InvalidCartException("cart invalid");
        cart.setCarrier(value);
        cart = tenantCartRepository.save(cart);
        return cartMapper.toTenanteDto(cart);
    }

    public BigDecimal getCarrierCost(String ref) {
        return Stream.concat(ship.stream(),pickup.stream()).filter(x-> x.getValue().equals(ref)).findFirst().get().getCost();
    }
    @Transactional(readOnly = true)
    public List<Carrier> findByStateCityWeight(String secureKey) throws InvalidCartException {
        Cart cart = cartRepository.findBySecureKey(secureKey).orElse(null);
        if(cart == null)
            throw new InvalidCartException("cart invalid");

        if(cart.getCarrier() != null && cart.getCarrier().equalsIgnoreCase("pickup")) {
            return pickup;
        }
        return ship;
    }
}
