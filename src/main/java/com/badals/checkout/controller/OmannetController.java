package com.badals.checkout.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import trust2.iPayPipe;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;

@Controller
@RequestMapping("/omannet")
public class OmannetController {
   Logger _logger = LoggerFactory.getLogger(OmannetController.class);
   
   
   
   /*
    * 
    * receiptUrl => $receiptURL,
                        errorURL => $errorURL,
                        aliasName => $aliasName,
                        action => $action,
                        udf1 => $Udf1,
                        udf2 => $Udf2,
                        udf3 => $Udf3,
                        udf4 => $Udf4,
                        udf5 => $Udf5
    * 
    * 
    * 
    */
   
   
   @RequestMapping(value="/verification")
   public @ResponseBody
   String verify(
         @RequestParam("receiptURL") String receiptURL,
         @RequestParam("errorURL") String errorURL,
         @RequestParam("aliasName") String aliasName,
         @RequestParam("action") String action,
         @RequestParam("udf1") String udf1,
         @RequestParam("udf2") String udf2,
         @RequestParam("udf3") String udf3,
         @RequestParam("udf4") String udf4,
         @RequestParam("udf5") String udf5,
         @RequestParam("amount") String amount,
         @RequestParam("trackid") String  trackid)
   throws IOException, URISyntaxException, ParseException {

      iPayPipe pipe = new iPayPipe();
      URL url = this.getClass().getClassLoader().getResource("/certs");
      
      String currency = "512"; //(ex: "512")
      String language = "USA";
      
      pipe.setResourcePath(url.getPath());
      pipe.setKeystorePath(url.getPath());
      pipe.setAlias(aliasName);
      pipe.setAction( action );
      pipe.setCurrency(currency);
      pipe.setLanguage(language);
      pipe.setResponseURL( receiptURL );
      pipe.setErrorURL(errorURL);
      pipe.setAmt(amount);
      pipe.setTrackId(trackid);
      pipe.setUdf1 (udf1);
      pipe.setUdf2(udf2);
      pipe.setUdf3(udf3);
      pipe.setUdf4(udf4);
      
      pipe.performPaymentInitializationHTTP();
      
      return pipe.getWebAddress();
      
      //return aliasName + " and aliali " + udf1;
   }
   
   @RequestMapping(value="/return")
   public @ResponseBody
   String parseReturn(
         @RequestParam("aliasName") String aliasName,
         @RequestParam("action") String action,
         @RequestParam("trandata") String  trandata)
   throws IOException,  URISyntaxException, ParseException {

      iPayPipe pipe = new iPayPipe();
      URL url = this.getClass().getClassLoader().getResource("/certs");
      
      String currency = "512"; //(ex: "512")
      String language = "USA";
      
      pipe.setResourcePath(url.getPath());
      pipe.setKeystorePath(url.getPath());
      pipe.setAlias(aliasName);

      
      pipe.parseEncryptedRequest(trandata);
      
      return pipe.getUdf1()+":"+pipe.getResult()+":"+pipe.getTransId()+":"+pipe.getPaymentId()+":"+pipe.getAmt()+":"+pipe.getError_text();
      
      //return aliasName + " and aliali " + udf1;
   }

}
