package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class JustificativaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Justificativa.class);
        Justificativa justificativa1 = new Justificativa();
        justificativa1.setId(1L);
        Justificativa justificativa2 = new Justificativa();
        justificativa2.setId(justificativa1.getId());
        assertThat(justificativa1).isEqualTo(justificativa2);
        justificativa2.setId(2L);
        assertThat(justificativa1).isNotEqualTo(justificativa2);
        justificativa1.setId(null);
        assertThat(justificativa1).isNotEqualTo(justificativa2);
    }
}
