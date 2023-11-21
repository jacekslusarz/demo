package com.jac.boot.demo.security;

import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@EnableWebSecurity
class SecurityConfiguration {



  private final KeycloakLogoutHandler keycloakLogoutHandler;


  SecurityConfiguration(KeycloakLogoutHandler keycloakLogoutHandler) {
    this.keycloakLogoutHandler = keycloakLogoutHandler;
  }

  @Bean
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .build();
  }

  @Order(1)
  @Bean
  public SecurityFilterChain clientFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .requestMatchers(new AntPathRequestMatcher("/"), new AntPathRequestMatcher("/groups"))
        .permitAll()
        .anyRequest()
        .authenticated();
    http.oauth2Login((oauth2Login) -> oauth2Login
        .userInfoEndpoint((userInfo) -> userInfo
            .userAuthoritiesMapper(grantedAuthoritiesMapper())));
    return http.build();
  }

  private GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
    return (authorities) -> {
      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

      authorities.forEach((authority) -> {
        GrantedAuthority mappedAuthority;

        if (authority instanceof OidcUserAuthority) {
          OidcUserAuthority userAuthority = (OidcUserAuthority) authority;
          mappedAuthority = new OidcUserAuthority(
              "ROLE_USER", userAuthority.getIdToken(), userAuthority.getUserInfo());
        } else if (authority instanceof OAuth2UserAuthority) {
          OAuth2UserAuthority userAuthority = (OAuth2UserAuthority) authority;
          mappedAuthority = new OAuth2UserAuthority(
              "ROLE_USER", userAuthority.getAttributes());
        } else {
          mappedAuthority = authority;
        }

        mappedAuthorities.add(mappedAuthority);
      });

      return mappedAuthorities;
    };
  }

  @Order(2)
  @Bean
  public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .requestMatchers(new AntPathRequestMatcher("/*"))
        .hasRole("USER")
        .anyRequest()
        .authenticated();
    http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
    return http.build();
  }

  /*  @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }*/

   /* @Override
    protected void configure(final HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .authorizeRequests()
                *//*.antMatchers("/info/*")
                .hasRole("USER")
                .antMatchers("/projects")
                .hasRole("ADMIN")*//*
                .anyRequest()
                .permitAll();
    }
*/

}
