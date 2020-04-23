package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ResponsavelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Responsavel.class);
        Responsavel responsavel1 = new Responsavel();
        responsavel1.setId(1L);
        Responsavel responsavel2 = new Responsavel();
        responsavel2.setId(responsavel1.getId());
        assertThat(responsavel1).isEqualTo(responsavel2);
        responsavel2.setId(2L);
        assertThat(responsavel1).isNotEqualTo(responsavel2);
        responsavel1.setId(null);
        assertThat(responsavel1).isNotEqualTo(responsavel2);
    }
}
