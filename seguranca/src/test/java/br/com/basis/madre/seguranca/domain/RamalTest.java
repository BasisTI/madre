package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class RamalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ramal.class);
        Ramal ramal1 = new Ramal();
        ramal1.setId(1L);
        Ramal ramal2 = new Ramal();
        ramal2.setId(ramal1.getId());
        assertThat(ramal1).isEqualTo(ramal2);
        ramal2.setId(2L);
        assertThat(ramal1).isNotEqualTo(ramal2);
        ramal1.setId(null);
        assertThat(ramal1).isNotEqualTo(ramal2);
    }
}
