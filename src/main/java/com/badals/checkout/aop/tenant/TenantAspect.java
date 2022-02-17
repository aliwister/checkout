package com.badals.checkout.aop.tenant;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Aspect
@Component
public class TenantAspect {

    @PersistenceContext
    private EntityManager entityManager;


    private final String fieldName =  "tenantId";

    private final Logger log = LoggerFactory.getLogger(TenantAspect.class);

    /**
     * Run method if User service is hit.
     * Filter users based on which tenant the user is associated with.
     * Skip filter if user has no tenant
     */
/*    @Before("execution(* com.badals.shop.service.UserService.*(..)) || execution(* com.badals.shop.security.DomainUserDetailsService.*(..))")
    public void beforeExecution() throws Throwable {
       if (TenantContext.getCurrentProfile() != null) {
         Filter filter = entityManager.unwrap(Session.class).enableFilter("TENANT_FILTER");
         filter.setParameter("tenantId", TenantContext.getCurrentProfile());
         filter.validate();
		}
    }*/

    @Before("execution(* com.badals.checkout.service.TenantCheckoutService.*(..)) || execution(* com.badals.checkout.service.CarrierService.*(..))")
    public void beforeGraphQLExecution() throws Throwable {
       if (TenantContext.getCurrentProfile() != null) {
         Filter filter = entityManager.unwrap(Session.class).enableFilter("tenantFilter");
         filter.setParameter("tenantId", TenantContext.getCurrentProfile());
         filter.validate();
		}
    }
}
