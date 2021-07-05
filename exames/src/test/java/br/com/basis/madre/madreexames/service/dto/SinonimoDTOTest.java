package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class SinonimoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SinonimoDTO.class);
        SinonimoDTO sinonimoDTO1 = new SinonimoDTO();
        sinonimoDTO1.setId(1L);
        SinonimoDTO sinonimoDTO2 = new SinonimoDTO();
        assertThat(sinonimoDTO1).isNotEqualTo(sinonimoDTO2);
        sinonimoDTO2.setId(sinonimoDTO1.getId());
        assertThat(sinonimoDTO1).isEqualTo(sinonimoDTO2);
        sinonimoDTO2.setId(2L);
        assertThat(sinonimoDTO1).isNotEqualTo(sinonimoDTO2);
        sinonimoDTO1.setId(null);
        assertThat(sinonimoDTO1).isNotEqualTo(sinonimoDTO2);
    }
}
