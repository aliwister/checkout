package com.badals.checkout;


import com.badals.checkout.security.JWTConfigurer;
import com.badals.checkout.security.TokenProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final CookieFilter cookieFilter;
    private final TokenProvider tokenProvider;

    public SecurityConfiguration(CorsFilter corsFilter, CookieFilter cookieFilter, TokenProvider tokenProvider) {
        this.corsFilter = corsFilter;
        this.cookieFilter = cookieFilter;
        this.tokenProvider = tokenProvider;
    }

/*    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(cookieFilter, CorsFilter.class).antMatcher("/graphql")
            .exceptionHandling()
            //.authenticationEntryPoint(problemSupport)
            //.accessDeniedHandler(problemSupport)
        .and()
            .headers()
            .contentSecurityPolicy("script-src 'self' 'unsafe-inline' 'unsafe-eval' http://cdn.checkout.com/js/framesv2.min.js https://js.checkout.com/framesv2/log; style-src 'self' 'unsafe-inline' http://fonts.googleapis.com/css ; data:")
        .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
        .and()
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
        .and()
            .frameOptions()
            .deny()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/checkout/**").permitAll()
            .antMatchers("/test/**").permitAll()
                .antMatchers("/api/checkout/**/**").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/stripe-support/**").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/graphql").permitAll()
            .antMatchers("/api/users/current").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            //.antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .and()
            .httpBasic()
        .and()
                .apply(securityConfigurerAdapter());
        // @formatter:on
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

}
