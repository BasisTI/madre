package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ProcedenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procedencia.class);
        Procedencia procedencia1 = new Procedencia();
        procedencia1.setId(1L);
        Procedencia procedencia2 = new Procedencia();
        procedencia2.setId(procedencia1.getId());
        assertThat(procedencia1).isEqualTo(procedencia2);
        procedencia2.setId(2L);
        assertThat(procedencia1).isNotEqualTo(procedencia2);
        procedencia1.setId(null);
        assertThat(procedencia1).isNotEqualTo(procedencia2);
    }
}
