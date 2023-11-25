package com.badals.checkout.controller;


import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.dto.CheckoutDTO;
import com.badals.checkout.service.dto.OrderDTO;
import com.badals.checkout.service.mapper.CartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

@Controller
@RequestMapping("checkout")
public class CheckoutController {
    private final Logger log = LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    CartMapper cartMapper;

    @Value("${app.faceurl}")
    String faceUrl;

    @PostMapping(value = "/request/{token}", produces = "application/json")
    public @ResponseBody CheckoutDTO request(@PathVariable(value="token") String token, @RequestBody CartDTO cartDTO) throws URISyntaxException {
        log.info("REST request to save Address : {}", cartDTO);
        CheckoutDTO dto = new CheckoutDTO();
        dto.setRedirect("http://www.google.com");

/*        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);*/

        return dto;
        //final HttpHeaders httpHeaders= new HttpHeaders();
        //httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //return new ResponseEntity<CheckoutDTO>(dto, httpHeaders, HttpStatus.OK);
    }

/*    @GetMapping("/start")
    public String start(@RequestParam(required=true) String token, Model model) {
        log.info("I'm a token {}", token);
        //log.debug("REST request to create cart : {}", cart);
        model.addAttribute("token", token);
        model.addAttribute("guid",  UUID.randomUUID().toString());
        return "checkout";
    }

    @GetMapping("/update")
    public String update(@RequestParam(required=true) String token, Model model) {
        log.info("I'm a token {}", token);
        //log.debug("REST request to create cart : {}", cart);
        model.addAttribute("order", token);
        return "checkout";
    }*/

    @RequestMapping("/ping")
    public String ping(Model model) {
        //log.info("I'm a token {}", token);
        //log.debug("REST request to create cart : {}", cart);
        model.addAttribute("message", "I'm Alive");
        model.addAttribute("token", "abc");
        return "index";
    }


    @GetMapping("/testtesttesttest")
    public void testRedirect(@RequestParam(required=true, name="cko-payment-token") String paymentToken, HttpServletResponse response) throws IOException {
        log.info("I'm a token {}", paymentToken);
        //orderService.c
        //log.debug("REST request to create cart : {}", cart);
        //model.addAttribute("token", token);
        //Order order = cartService.createOrderWithPaymentByPaymentToken(paymentToken);
        response.sendRedirect(faceUrl+"order-received?ref="+"abc+&key=efg");
        //return "confirmation";
    }

    @GetMapping("/checkout-com-failure")
    public String checkoutFailure(@RequestParam(required=true, name="cko-payment-token") String paymentToken) {
        return "failure";
    }
}
