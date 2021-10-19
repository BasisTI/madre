package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class SituacaoDeLeitoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituacaoDeLeitoDTO.class);
        SituacaoDeLeitoDTO situacaoDeLeitoDTO1 = new SituacaoDeLeitoDTO();
        situacaoDeLeitoDTO1.setId(1L);
        SituacaoDeLeitoDTO situacaoDeLeitoDTO2 = new SituacaoDeLeitoDTO();
        assertThat(situacaoDeLeitoDTO1).isNotEqualTo(situacaoDeLeitoDTO2);
        situacaoDeLeitoDTO2.setId(situacaoDeLeitoDTO1.getId());
        assertThat(situacaoDeLeitoDTO1).isEqualTo(situacaoDeLeitoDTO2);
        situacaoDeLeitoDTO2.setId(2L);
        assertThat(situacaoDeLeitoDTO1).isNotEqualTo(situacaoDeLeitoDTO2);
        situacaoDeLeitoDTO1.setId(null);
        assertThat(situacaoDeLeitoDTO1).isNotEqualTo(situacaoDeLeitoDTO2);
    }
}
