package com.xiaobai.pms.security;

import com.alibaba.fastjson.JSONObject;
import com.xiaobai.pms.dto.response.Response;
import com.xiaobai.pms.security.api.ApiJWTAuthenticationFilter;
import com.xiaobai.pms.security.api.ApiJWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by Arpit Khandelwal.
 */
@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Configuration
    @Order(1)
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(bCryptPasswordEncoder);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(
                    "/api/v1/**/get/**",
                    "/api/v1/**/list/**",
                    "http://localhost:8080/file/"
            );
        }

        // @formatter:off
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/v1/user/signup").permitAll()
                    .and()
                    .antMatcher("/api/**")
                    .exceptionHandling()
                    .authenticationEntryPoint((req, rsp, e) -> {
                        rsp.setHeader("Content-Type", "application/json;charset=utf-8");
                        PrintWriter writer = rsp.getWriter();
                        writer.write(JSONObject.toJSONString(Response.error().setMessage("EntryPoint,返回!")));
                        writer.flush();
                        writer.close();
                    })
                    .and()
                    .addFilter(new ApiJWTAuthenticationFilter(authenticationManager()))
                    .addFilter(new ApiJWTAuthorizationFilter(authenticationManager()))
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        // @formatter:on
    }

}
