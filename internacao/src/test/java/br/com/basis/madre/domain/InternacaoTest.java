package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class InternacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Internacao.class);
        Internacao internacao1 = new Internacao();
        internacao1.setId(1L);
        Internacao internacao2 = new Internacao();
        internacao2.setId(internacao1.getId());
        assertThat(internacao1).isEqualTo(internacao2);
        internacao2.setId(2L);
        assertThat(internacao1).isNotEqualTo(internacao2);
        internacao1.setId(null);
        assertThat(internacao1).isNotEqualTo(internacao2);
    }
}
