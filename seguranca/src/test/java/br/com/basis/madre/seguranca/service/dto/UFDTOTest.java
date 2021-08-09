package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class UFDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UFDTO.class);
        UFDTO uFDTO1 = new UFDTO();
        uFDTO1.setId(1L);
        UFDTO uFDTO2 = new UFDTO();
        assertThat(uFDTO1).isNotEqualTo(uFDTO2);
        uFDTO2.setId(uFDTO1.getId());
        assertThat(uFDTO1).isEqualTo(uFDTO2);
        uFDTO2.setId(2L);
        assertThat(uFDTO1).isNotEqualTo(uFDTO2);
        uFDTO1.setId(null);
        assertThat(uFDTO1).isNotEqualTo(uFDTO2);
    }
}
