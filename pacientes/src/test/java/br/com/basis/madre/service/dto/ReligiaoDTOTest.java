package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ReligiaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReligiaoDTO.class);
        ReligiaoDTO religiaoDTO1 = new ReligiaoDTO();
        religiaoDTO1.setId(1L);
        ReligiaoDTO religiaoDTO2 = new ReligiaoDTO();
        assertThat(religiaoDTO1).isNotEqualTo(religiaoDTO2);
        religiaoDTO2.setId(religiaoDTO1.getId());
        assertThat(religiaoDTO1).isEqualTo(religiaoDTO2);
        religiaoDTO2.setId(2L);
        assertThat(religiaoDTO1).isNotEqualTo(religiaoDTO2);
        religiaoDTO1.setId(null);
        assertThat(religiaoDTO1).isNotEqualTo(religiaoDTO2);
    }
}
