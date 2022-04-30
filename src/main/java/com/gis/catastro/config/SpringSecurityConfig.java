package com.gis.catastro.config;

import com.gis.catastro.security.filter.JWTAuthenticationFilter;
import com.gis.catastro.security.filter.JWTAuthorizationFilter;
import com.gis.catastro.security.service.JWTService;
import com.gis.catastro.security.service.JpaUserDeatilsService;
import com.gis.catastro.service.*;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/",
            "/locale",
            "/api/web/**",
            "/api/login"
    };
    @Autowired
    private JpaUserDeatilsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ConnectionLogService connectionLogService;

    @Autowired
    private DomainValueService domainValueService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and()
                .authorizeRequests().antMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService, userService, resourceService, permissionService, connectionLogService, domainValueService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService, userService))
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS
        );
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception, NoSuchBeanDefinitionException {
        build.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("*"));
        configuration.setAllowedHeaders(ImmutableList.of("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
