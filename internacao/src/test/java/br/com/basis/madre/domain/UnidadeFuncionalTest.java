package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class UnidadeFuncionalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeFuncional.class);
        UnidadeFuncional unidadeFuncional1 = new UnidadeFuncional();
        unidadeFuncional1.setId(1L);
        UnidadeFuncional unidadeFuncional2 = new UnidadeFuncional();
        unidadeFuncional2.setId(unidadeFuncional1.getId());
        assertThat(unidadeFuncional1).isEqualTo(unidadeFuncional2);
        unidadeFuncional2.setId(2L);
        assertThat(unidadeFuncional1).isNotEqualTo(unidadeFuncional2);
        unidadeFuncional1.setId(null);
        assertThat(unidadeFuncional1).isNotEqualTo(unidadeFuncional2);
    }
}
