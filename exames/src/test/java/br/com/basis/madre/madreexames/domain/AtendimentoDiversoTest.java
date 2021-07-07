package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class AtendimentoDiversoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtendimentoDiverso.class);
        AtendimentoDiverso atendimentoDiverso1 = new AtendimentoDiverso();
        atendimentoDiverso1.setId(1L);
        AtendimentoDiverso atendimentoDiverso2 = new AtendimentoDiverso();
        atendimentoDiverso2.setId(atendimentoDiverso1.getId());
        assertThat(atendimentoDiverso1).isEqualTo(atendimentoDiverso2);
        atendimentoDiverso2.setId(2L);
        assertThat(atendimentoDiverso1).isNotEqualTo(atendimentoDiverso2);
        atendimentoDiverso1.setId(null);
        assertThat(atendimentoDiverso1).isNotEqualTo(atendimentoDiverso2);
    }
}
