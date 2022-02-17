package com.badals.checkout.service;

import com.badals.checkout.domain.Cart;
import com.badals.checkout.domain.Checkout;
import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.projection.ShipRate;
import com.badals.checkout.repository.CarrierRepository;
import com.badals.checkout.service.dto.CarrierDTO;
import com.badals.checkout.repository.CartRepository;
import com.badals.checkout.repository.CheckoutRepository;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.mapper.CarrierMapper;
import com.badals.checkout.service.mapper.CartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CarrierService {
    private final Logger log = LoggerFactory.getLogger(CarrierService.class);

    private final CartRepository cartRepository;
    private final CarrierRepository carrierRepository;;
    private final CheckoutRepository checkoutRepository;
    private final CarrierMapper carrierMapper;
    private final CartMapper cartMapper;

    List<CarrierDTO> ship = new ArrayList<CarrierDTO>(){{
        add(new CarrierDTO(-10,"Badals.com Home Delivery -- Muscat Only", "BADALSMUSCAT", BigDecimal.valueOf(1), ""));
        //add(new Carrier("Pickup from our showroom", "PICKUP", BigDecimal.ZERO, ""));
        add(new CarrierDTO(-9, "Dakhiliya Home Delivery", "Dakhiliya", BigDecimal.valueOf(2),""));
        add(new CarrierDTO(-8, "Batina Home Delivery", "Batina", BigDecimal.valueOf(2),""));
        add(new CarrierDTO(-7, "Sharqiya Home Delivery", "Sharqiya", BigDecimal.valueOf(2),""));
        add(new CarrierDTO(-6, "Wusta Home Delivery", "Wusta", BigDecimal.valueOf(2.5),""));
        add(new CarrierDTO(-5, "Dhahira Home Delivery", "Dhahira", BigDecimal.valueOf(2),""));
        add(new CarrierDTO(-4, "Buraymi Home Delivery", "BURAYMI", BigDecimal.valueOf(2),""));
        add(new CarrierDTO(-3, "DHL Home Delivery (Salalah/Sohar ONLY)", "DHL", BigDecimal.valueOf(3),""));
    }};
    List<CarrierDTO> pickup = new ArrayList<CarrierDTO>(){{
        add(new CarrierDTO(-10, "Store Pickup", "PICKUP", BigDecimal.valueOf(0), ""));
    }};

    public CarrierService(CartRepository cartRepository, CarrierRepository carrierRepository, CheckoutRepository checkoutRepository, CarrierMapper carrierMapper, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.carrierRepository = carrierRepository;
        this.checkoutRepository = checkoutRepository;
        this.carrierMapper = carrierMapper;
        this.cartMapper = cartMapper;
    }

    @Transactional(readOnly = true)
    public List<CarrierDTO> findByStateCityWeight(String state, String city, BigDecimal weight, boolean isPickup ) {
        if(isPickup) {
            return pickup;
        }
        return ship;
    }


    @Transactional
    public CartDTO setCarrier(String value, String secureKey) throws InvalidCartException {
        Cart cart = cartRepository.findBySecureKey(secureKey).orElse(null);
        if(cart == null)
            throw new InvalidCartException("cart invalid");
        cart.setCarrier(value);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public List<CarrierDTO> getCarriers(String token) {
        Checkout cart = checkoutRepository.findBySecureKey(token).get();
        List<ShipRate> rates = getRates(cart);

        List<CarrierDTO> carrierDTOS = rates.stream().map(carrierMapper::toDtoFromShipRate).collect(Collectors.toList());
        return carrierDTOS;
    }

    private List<ShipRate> getRates(Checkout cart) {
        Address address = cart.getDeliveryAddress();
        if (cart.getDeliveryAddressId() != null && cart.getDeliveryAddressId() > -1) {
            address = cart.getAddresses().stream().filter(x -> x.getId() == cart.getDeliveryAddressId()).findFirst().get();
        }
        String level4 = address.getCity();
        String level3 = address.getState();
        String level2 = address.getCountry();

        return carrierRepository.getShippingRates(level3, cart.getCartWeight());
    }

    @Transactional
    public CartDTO setTenantCarrier(Long value, String secureKey) throws InvalidCartException {
        Checkout cart = checkoutRepository.findBySecureKey(secureKey).orElse(null);
        if(cart == null)
            throw new InvalidCartException("cart invalid");


        List<ShipRate> rates = getRates(cart);
        ShipRate rate = rates.stream().filter(c -> c.getId()==value).findFirst().get();
        cart.setCarrier(rate.getCarrierRef());
        cart.setCarrierService(rate.getRateName());
        cart.setCarrierRate(rate.getPrice());
        checkoutRepository.save(cart);
        return cartMapper.toTenanteDto(cart);
    }

    public BigDecimal getTenantCarrierCost(String ref) {
        return Stream.concat(ship.stream(),pickup.stream()).filter(x-> x.getValue().equals(ref)).findFirst().get().getCost();
    }

    public BigDecimal getCarrierCost(String ref) {
        return Stream.concat(ship.stream(),pickup.stream()).filter(x-> x.getValue().equals(ref)).findFirst().get().getCost();
    }
    @Transactional(readOnly = true)
    public List<CarrierDTO> findByStateCityWeight(String secureKey) throws InvalidCartException {
        Cart cart = cartRepository.findBySecureKey(secureKey).orElse(null);
        if(cart == null)
            throw new InvalidCartException("cart invalid");

        if(cart.getCarrier() != null && cart.getCarrier().equalsIgnoreCase("pickup")) {
            return pickup;
        }
        return ship;
    }
}
