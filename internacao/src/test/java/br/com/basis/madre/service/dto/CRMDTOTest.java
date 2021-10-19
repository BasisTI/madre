package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class CRMDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRMDTO.class);
        CRMDTO cRMDTO1 = new CRMDTO();
        cRMDTO1.setId(1L);
        CRMDTO cRMDTO2 = new CRMDTO();
        assertThat(cRMDTO1).isNotEqualTo(cRMDTO2);
        cRMDTO2.setId(cRMDTO1.getId());
        assertThat(cRMDTO1).isEqualTo(cRMDTO2);
        cRMDTO2.setId(2L);
        assertThat(cRMDTO1).isNotEqualTo(cRMDTO2);
        cRMDTO1.setId(null);
        assertThat(cRMDTO1).isNotEqualTo(cRMDTO2);
    }
}
