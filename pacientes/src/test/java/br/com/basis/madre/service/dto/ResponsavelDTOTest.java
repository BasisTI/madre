package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ResponsavelDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelDTO.class);
        ResponsavelDTO responsavelDTO1 = new ResponsavelDTO();
        responsavelDTO1.setId(1L);
        ResponsavelDTO responsavelDTO2 = new ResponsavelDTO();
        assertThat(responsavelDTO1).isNotEqualTo(responsavelDTO2);
        responsavelDTO2.setId(responsavelDTO1.getId());
        assertThat(responsavelDTO1).isEqualTo(responsavelDTO2);
        responsavelDTO2.setId(2L);
        assertThat(responsavelDTO1).isNotEqualTo(responsavelDTO2);
        responsavelDTO1.setId(null);
        assertThat(responsavelDTO1).isNotEqualTo(responsavelDTO2);
    }
}
