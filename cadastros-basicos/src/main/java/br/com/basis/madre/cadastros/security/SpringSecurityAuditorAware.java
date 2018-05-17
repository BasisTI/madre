package br.com.basis.madre.cadastros.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import br.com.basis.madre.cadastros.config.Constants;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        return SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT);
    }
}
