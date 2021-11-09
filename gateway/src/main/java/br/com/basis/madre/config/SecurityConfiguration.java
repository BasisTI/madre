package br.com.basis.madre.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import br.com.basis.madre.security.AuthoritiesConstants;
import br.com.basis.madre.service.UserService;
import br.gov.nuvem.security.jwt.web.TokenProvider;
import br.gov.nuvem.security.jwt.web.TokenResolver;
import br.gov.nuvem.security.sso.config.WebSecurityConfigurerAdapterNuvem;

@Component
public class SecurityConfiguration extends br.gov.nuvem.security.sso.config.SecurityConfiguration {

    @Autowired
    private UserService userService;

    public SecurityConfiguration(
        TokenProvider tokenProvider,
        TokenResolver tokenResolver,
        WebSecurityConfigurerAdapterNuvem webSecurityConfigurerAdapterNuvem) {
        super(tokenProvider, tokenResolver, webSecurityConfigurerAdapterNuvem);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoder());
    }


    @Override
    public void configureApp(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/**").permitAll()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);
    }

}
