package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ProcedimentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procedimento.class);
        Procedimento procedimento1 = new Procedimento();
        procedimento1.setId(1L);
        Procedimento procedimento2 = new Procedimento();
        procedimento2.setId(procedimento1.getId());
        assertThat(procedimento1).isEqualTo(procedimento2);
        procedimento2.setId(2L);
        assertThat(procedimento1).isNotEqualTo(procedimento2);
        procedimento1.setId(null);
        assertThat(procedimento1).isNotEqualTo(procedimento2);
    }
}
