package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class CIDDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIDDTO.class);
        CIDDTO cIDDTO1 = new CIDDTO();
        cIDDTO1.setId(1L);
        CIDDTO cIDDTO2 = new CIDDTO();
        assertThat(cIDDTO1).isNotEqualTo(cIDDTO2);
        cIDDTO2.setId(cIDDTO1.getId());
        assertThat(cIDDTO1).isEqualTo(cIDDTO2);
        cIDDTO2.setId(2L);
        assertThat(cIDDTO1).isNotEqualTo(cIDDTO2);
        cIDDTO1.setId(null);
        assertThat(cIDDTO1).isNotEqualTo(cIDDTO2);
    }
}
