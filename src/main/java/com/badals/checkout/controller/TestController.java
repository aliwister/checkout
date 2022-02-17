package com.badals.checkout.controller;

import com.badals.checkout.domain.Zone;
import com.badals.checkout.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("test")
public class TestController {

   @Autowired
   ZoneRepository zoneRepository;

   @GetMapping(value="/countries", produces = "application/json")
   public @ResponseBody
   List<Zone> test() {
      return zoneRepository.findAllByLevelAndActiveIsTrue(Zone.ZoneLevel.COUNTRY);
   }
}
