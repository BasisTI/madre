package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class GenitoresDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenitoresDTO.class);
        GenitoresDTO genitoresDTO1 = new GenitoresDTO();
        genitoresDTO1.setId(1L);
        GenitoresDTO genitoresDTO2 = new GenitoresDTO();
        assertThat(genitoresDTO1).isNotEqualTo(genitoresDTO2);
        genitoresDTO2.setId(genitoresDTO1.getId());
        assertThat(genitoresDTO1).isEqualTo(genitoresDTO2);
        genitoresDTO2.setId(2L);
        assertThat(genitoresDTO1).isNotEqualTo(genitoresDTO2);
        genitoresDTO1.setId(null);
        assertThat(genitoresDTO1).isNotEqualTo(genitoresDTO2);
    }
}
