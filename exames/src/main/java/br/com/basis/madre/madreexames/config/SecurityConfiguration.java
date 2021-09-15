package br.com.basis.madre.madreexames.config;

import br.gov.nuvem.comum.microsservico.config.SecurityConfigurationComum;
import br.gov.nuvem.security.jwt.web.TokenProvider;
import br.gov.nuvem.security.jwt.web.TokenResolver;
import br.gov.nuvem.security.sso.config.AuthoritiesConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
public class SecurityConfiguration extends SecurityConfigurationComum {

    public SecurityConfiguration(
        TokenProvider tokenProvider,
        TokenResolver tokenResolver,
        SecurityProblemSupport problemSupport,
        AuthenticationEntryPoint authenticationEntryPoint) {
        super(tokenProvider, tokenResolver, problemSupport, authenticationEntryPoint);
    }

    @Override
    public void configureApp(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);
    }

}
