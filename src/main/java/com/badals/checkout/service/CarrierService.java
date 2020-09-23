package com.badals.checkout.service;

import com.badals.checkout.domain.Cart;
import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.Carrier;
import com.badals.checkout.repository.CartRepository;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.mapper.CartMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CarrierService {
    private final Logger log = LoggerFactory.getLogger(CarrierService.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartMapper cartMapper;

    List<Carrier> list = new ArrayList<Carrier>(){{
        add(new Carrier("Badals.com Home Delivery -- Muscat Only", "BADALSMUSCAT", BigDecimal.valueOf(1), ""));
        //add(new Carrier("Pickup from our showroom", "PICKUP", BigDecimal.ZERO, ""));
        add(new Carrier("Dakhiliya/Batina Home Delivery", "BADALSMANDOOB", BigDecimal.valueOf(2),""));
        add(new Carrier("Sharqiya/Wusta Home Delivery", "BADALSMANDOOB", BigDecimal.valueOf(2),""));
        add(new Carrier("Dhahira/Buraymi Home Delivery", "BADALSMANDOOB", BigDecimal.valueOf(2),""));
        add(new Carrier("DHL Home Delivery (Salalah/Sohar ONLY)", "DHL", BigDecimal.valueOf(3),""));
    }};

    @Transactional(readOnly = true)
    public List<Carrier> findByStateCityWeight(String state, String city, BigDecimal weight) {
        return list;
    }

    public CartDTO setCarrier(String value, String secureKey) throws InvalidCartException {
        Cart cart = cartRepository.findBySecureKey(secureKey).orElse(null);
        if(cart == null)
            throw new InvalidCartException("cart invalid");
        cart.setCarrier(value);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public BigDecimal getCarrierCost(String ref) {
        return list.stream().filter(x-> x.getValue().equals(ref)).findFirst().get().getCost();
    }
}
