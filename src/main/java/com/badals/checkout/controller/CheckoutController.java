package com.badals.checkout.controller;

import com.badals.checkout.domain.Cart;
import com.badals.checkout.repository.CartRepository;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.dto.CheckoutDTO;
import com.badals.checkout.service.mapper.CartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Controller
@RequestMapping("checkout")
public class CheckoutController {
    private final Logger log = LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartMapper cartMapper;

    @PostMapping(value = "/request/{token}", produces = "application/json")
    public @ResponseBody CheckoutDTO request(@PathVariable(value="token") String token, @RequestBody CartDTO cartDTO) throws URISyntaxException {
        log.info("REST request to save Address : {}", cartDTO);
        CheckoutDTO dto = new CheckoutDTO();
        dto.setRedirect("http://www.google.com");

        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);

        return dto;
        //final HttpHeaders httpHeaders= new HttpHeaders();
        //httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //return new ResponseEntity<CheckoutDTO>(dto, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping("/start/{token}")
    public String start(@PathVariable(value="token") String token) {
        log.info("I'm a token");
        //log.debug("REST request to create cart : {}", cart);
        return "checkout";
    }
}
