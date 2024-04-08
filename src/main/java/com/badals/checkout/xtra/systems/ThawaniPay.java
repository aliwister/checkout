package com.badals.checkout.xtra.systems;

import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.service.InvalidCartException;
import com.badals.checkout.service.LockedCartException;
import com.badals.checkout.service.TenantCheckoutService;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.integration.payment.thawani.ThawaniPaymentService;
import com.badals.checkout.service.integration.payment.thawani.dto.request.CreateCheckoutMetaData;
import com.badals.checkout.service.integration.payment.thawani.dto.request.CreateCheckoutSessionBody;
import com.badals.checkout.service.integration.payment.thawani.dto.request.Product;
import com.badals.checkout.service.integration.payment.thawani.dto.response.CreateCheckoutSessionResponse;
import com.badals.checkout.service.integration.payment.thawani.dto.response.ThawaniResponse;
import com.badals.checkout.xtra.PaymentSystem;
import com.badals.checkout.xtra.PaymentType;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ThawaniPay extends PaymentSystem {
    private TenantCheckoutService cartService;
    private ThawaniPaymentService thawaniPaymentService;
    private TenantCheckoutService checkoutService;

    public ThawaniPay(TenantCheckoutService cartService, ThawaniPaymentService thawaniPaymentService, TenantCheckoutService checkoutService) {
        this.cartService = cartService;
        this.thawaniPaymentService = thawaniPaymentService;
        this.checkoutService = checkoutService;
    }

    public synchronized PaymentResponsePayload processPayment(String secretKey, String secureKey, boolean isWebsite) {
        // todo check if cart currency is omr
        // todo handle scenario where cart has discount
        // todo handle failure scenarios to unlock cart
        CartDTO cart = null;
        try {
            String baseUrl = TenantCheckoutService.buildProfileBaseUrl(checkoutService.getTenant());
            cart = cartService.findBySecureKeyWithLock(secureKey);
            List<Product> products = cart.getItems().stream().map(item ->
                                                                    Product.builder()
                                                                            .name(item.getName())
                                                                            .quantity(item.getQuantity().intValue())
                                                                            .unit_amount((item.getPrice().multiply(new BigDecimal(1000))).intValue())
                                                                            .build()).collect(Collectors.toList());
            if(cart.getCarrierRate() != null)
                products.add(
                        Product.builder()
                                .name("shipping")
                                .quantity(1)
                                .unit_amount(Double.valueOf(cart.getCarrierRate()*1000).intValue())
                                .build()
                );

            CreateCheckoutSessionBody createCheckoutSessionBody = CreateCheckoutSessionBody.builder()
                    .client_reference_id(cart.getId().toString())
                    .mode("payment")
                    .products(products)
                    .success_url(baseUrl + "/checkout/callbacks/thawani/success")
                    .cancel_url(baseUrl + "/checkout/callbacks/thawani/failure")
                    .metadata(CreateCheckoutMetaData.builder()
                            .client_checkout_id("")
                            .client_customer_id("")
                            .build())
                    .build();

            ThawaniResponse<CreateCheckoutSessionResponse> createCheckoutSessionResponse =
                    thawaniPaymentService.createCheckoutSession(secretKey, createCheckoutSessionBody);
            cartService.setPaymentToken(cart.getId(), PaymentType.THAWANI.ref, createCheckoutSessionResponse.getData().getSession_id());
            return paymentSucessful(createCheckoutSessionResponse.getData().getSession_id());
        } catch (InvalidCartException e) {
            return paymentDeclined(e.getMessage());
        } catch (LockedCartException e) {
            return paymentDeclined(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return paymentDeclined(e.getMessage());
        }
        finally {
            if(cart != null)
                cartService.unlock(secureKey);
        }

    }
}
