package br.com.basis.madre.config;

import br.gov.nuvem.security.jwt.web.TokenProvider;
import br.gov.nuvem.security.jwt.web.TokenResolver;
import br.gov.nuvem.security.sso.config.WebSecurityConfigurerAdapterNuvem;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import br.com.basis.madre.security.AuthoritiesConstants;

@Component
public class SecurityConfiguration extends br.gov.nuvem.security.sso.config.SecurityConfiguration {

    public SecurityConfiguration(
        TokenProvider tokenProvider,
        TokenResolver tokenResolver,
        WebSecurityConfigurerAdapterNuvem webSecurityConfigurerAdapterNuvem) {
        super(tokenProvider, tokenResolver, webSecurityConfigurerAdapterNuvem);
    }

    @Override
    public void configureApp(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/api/auth-info").permitAll()
//            .antMatchers("/api/**").authenticated()
            .antMatchers("/**").permitAll()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);
    }

}
