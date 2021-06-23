package br.com.basis.madre.exames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class CadaverTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cadaver.class);
        Cadaver cadaver1 = new Cadaver();
        cadaver1.setId(1L);
        Cadaver cadaver2 = new Cadaver();
        cadaver2.setId(cadaver1.getId());
        assertThat(cadaver1).isEqualTo(cadaver2);
        cadaver2.setId(2L);
        assertThat(cadaver1).isNotEqualTo(cadaver2);
        cadaver1.setId(null);
        assertThat(cadaver1).isNotEqualTo(cadaver2);
    }
}
