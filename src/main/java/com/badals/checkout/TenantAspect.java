package com.badals.checkout;

import com.badals.checkout.aop.tenant.TenantContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//@Aspect
//@Component
public class TenantAspect {

/*
   private final TenantRepository tenantRepository;

   @Autowired
   public TenantAspect(TenantRepository tenantRepository) {
      this.tenantRepository = tenantRepository;
   }
*/

   @Around(value = "execution(* com.badals.checkout.service.TenantCartService.*(..))")
   public Object assignForController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
      return assignTenant(proceedingJoinPoint);
   }

   private Object assignTenant(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
      try {
         HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
         String header = request.getRequestURI();
         System.out.println(header);

      } finally {
         Object retVal;
         retVal = proceedingJoinPoint.proceed();
         TenantContext.clear();
         return retVal;
      }
   }
}
