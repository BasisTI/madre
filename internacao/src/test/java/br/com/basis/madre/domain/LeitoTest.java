package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class LeitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leito.class);
        Leito leito1 = new Leito();
        leito1.setId(1L);
        Leito leito2 = new Leito();
        leito2.setId(leito1.getId());
        assertThat(leito1).isEqualTo(leito2);
        leito2.setId(2L);
        assertThat(leito1).isNotEqualTo(leito2);
        leito1.setId(null);
        assertThat(leito1).isNotEqualTo(leito2);
    }
}
