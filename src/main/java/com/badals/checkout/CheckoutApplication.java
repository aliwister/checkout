package com.badals.checkout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class CheckoutApplication {
	private static final Logger logger = LoggerFactory.getLogger(CheckoutApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CheckoutApplication.class, args);
	}

	//@Bean
	//public CheckoutApi checkoutApi(@Value("${checkoutcom.pk}") String _pk, @Value("${checkoutcom.sk}") String _sk) {
	//	return CheckoutApiImpl.create(_sk, false, _pk);
	//}
}
//5594 0606 7504 7208