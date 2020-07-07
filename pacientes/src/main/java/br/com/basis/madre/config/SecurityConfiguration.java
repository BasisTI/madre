package br.com.basis.madre.config;

import br.com.basis.madre.security.AuthoritiesConstants;
import br.gov.nuvem.comum.microsservico.config.SecurityConfigurationComum;
import br.gov.nuvem.security.jwt.web.TokenProvider;
import br.gov.nuvem.security.jwt.web.TokenResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
public class SecurityConfiguration extends SecurityConfigurationComum {

    public SecurityConfiguration(
        TokenProvider tokenProvider,
        TokenResolver tokenResolver,
        SecurityProblemSupport problemSupport) {
        super(tokenProvider, tokenResolver, problemSupport);
    }

    @Override
    public void configureApp(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);
    }

}
