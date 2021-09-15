package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class ConselhosProfissionaisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConselhosProfissionais.class);
        ConselhosProfissionais conselhosProfissionais1 = new ConselhosProfissionais();
        conselhosProfissionais1.setId(1L);
        ConselhosProfissionais conselhosProfissionais2 = new ConselhosProfissionais();
        conselhosProfissionais2.setId(conselhosProfissionais1.getId());
        assertThat(conselhosProfissionais1).isEqualTo(conselhosProfissionais2);
        conselhosProfissionais2.setId(2L);
        assertThat(conselhosProfissionais1).isNotEqualTo(conselhosProfissionais2);
        conselhosProfissionais1.setId(null);
        assertThat(conselhosProfissionais1).isNotEqualTo(conselhosProfissionais2);
    }
}
