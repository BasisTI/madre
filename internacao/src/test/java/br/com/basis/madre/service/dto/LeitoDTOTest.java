package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class LeitoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeitoDTO.class);
        LeitoDTO leitoDTO1 = new LeitoDTO();
        leitoDTO1.setId(1L);
        LeitoDTO leitoDTO2 = new LeitoDTO();
        assertThat(leitoDTO1).isNotEqualTo(leitoDTO2);
        leitoDTO2.setId(leitoDTO1.getId());
        assertThat(leitoDTO1).isEqualTo(leitoDTO2);
        leitoDTO2.setId(2L);
        assertThat(leitoDTO1).isNotEqualTo(leitoDTO2);
        leitoDTO1.setId(null);
        assertThat(leitoDTO1).isNotEqualTo(leitoDTO2);
    }
}
