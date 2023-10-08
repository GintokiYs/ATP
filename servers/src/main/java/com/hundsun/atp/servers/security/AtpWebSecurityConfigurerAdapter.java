package com.hundsun.atp.servers.security;


import com.hundsun.atp.common.constant.UrlConstants;
import com.hundsun.atp.servers.filter.AtpUsernamePasswordAuthenticationFilter;
import com.hundsun.atp.servers.handler.AtpAccessDeniedHandler;
import com.hundsun.atp.servers.handler.AtpLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class AtpWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    @Autowired
    private AtpLogoutSuccessHandler atpLogoutSuccessHandler;

    @Autowired
    private AtpAuthenticationEntryPoint atpAuthenticationEntryPoint;

    @Autowired
    private AtpAccessDeniedHandler atpAccessDeniedHandler;

    @Autowired
    private AtpUsernamePasswordAuthenticationFilter atpUsernamePasswordAuthenticationFilter;

    @Value("${spring.security.enabled:true}")
    private boolean securityEnabled;

    @Value("${spring.security.csrfEnabled:true}")
    private boolean csrfEnabled;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(new String[]{
                "/deploy/deployMode",
                "/openapi/**",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/**/*.png",
                "/**/*.jpg",
                "/css/**",
                "/fonts/**",
                "/img/**",
                "/js/**",
                "/static/**",
                "/error",
                "/webjars/**",
                "/resources/**",
                "/swagger-ui.html",
                "/v3/api-docs",
                "/swagger-resources/**",
                "/v2/api-docs"});
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (securityEnabled) {
            http.authorizeRequests()

                    .antMatchers(new String[]{"/login", "/logout", UrlConstants.LOGIN_PAGE}).permitAll()

                    .anyRequest().authenticated()

                    .and()

                    .logout().logoutUrl("/logout")
                    .logoutSuccessHandler(atpLogoutSuccessHandler)

                    .clearAuthentication(true)

                    .and()

                    .exceptionHandling().authenticationEntryPoint(atpAuthenticationEntryPoint)

                    .accessDeniedHandler(atpAccessDeniedHandler)
                    .and()
                    .cors().and()
                    .addFilter(atpUsernamePasswordAuthenticationFilter)
                    .csrf().disable();
            http.headers()
                    .contentTypeOptions()
                    .and()
                    .xssProtection()
                    .and()

                    .cacheControl()
                    .and()
                    .httpStrictTransportSecurity()
                    .and()

                    .frameOptions().disable();
        } else if (csrfEnabled) {
            http.authorizeRequests().anyRequest().permitAll();
        } else {
            http.csrf().disable().authorizeRequests().anyRequest().permitAll();
        }
    }
}