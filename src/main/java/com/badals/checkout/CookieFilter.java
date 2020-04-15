package com.badals.checkout;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CookieFilter implements Filter {

   private static final String UNIQUE_ONE_TIME_TOKEN = "nonce";

   private Set<String> tokens  = Collections.synchronizedSet(new HashSet<String>());

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest req = (HttpServletRequest) request;
     /* String token = null;
      Cookie[] cookies = ((HttpServletRequest) request).getCookies();
      for(Cookie c : cookies) {
         if(c.getName().equals(UNIQUE_ONE_TIME_TOKEN))
            token = c.getValue();
      }

      if (token == null || tokens.contains(token)) {
         //logger.error("token {} is null or allready used", token);
         HttpServletResponse res = (HttpServletResponse) response;
         res.sendError(202); // ignore request
         return;
      }
      tokens.add(token);*/
      chain.doFilter(request, response);
   }
   // other methods
}