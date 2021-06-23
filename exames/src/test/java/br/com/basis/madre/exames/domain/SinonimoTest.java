package br.com.basis.madre.exames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class SinonimoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sinonimo.class);
        Sinonimo sinonimo1 = new Sinonimo();
        sinonimo1.setId(1L);
        Sinonimo sinonimo2 = new Sinonimo();
        sinonimo2.setId(sinonimo1.getId());
        assertThat(sinonimo1).isEqualTo(sinonimo2);
        sinonimo2.setId(2L);
        assertThat(sinonimo1).isNotEqualTo(sinonimo2);
        sinonimo1.setId(null);
        assertThat(sinonimo1).isNotEqualTo(sinonimo2);
    }
}
