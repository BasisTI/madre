package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class MotivoDoBloqueioDeLeitoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivoDoBloqueioDeLeitoDTO.class);
        MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO1 = new MotivoDoBloqueioDeLeitoDTO();
        motivoDoBloqueioDeLeitoDTO1.setId(1L);
        MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO2 = new MotivoDoBloqueioDeLeitoDTO();
        assertThat(motivoDoBloqueioDeLeitoDTO1).isNotEqualTo(motivoDoBloqueioDeLeitoDTO2);
        motivoDoBloqueioDeLeitoDTO2.setId(motivoDoBloqueioDeLeitoDTO1.getId());
        assertThat(motivoDoBloqueioDeLeitoDTO1).isEqualTo(motivoDoBloqueioDeLeitoDTO2);
        motivoDoBloqueioDeLeitoDTO2.setId(2L);
        assertThat(motivoDoBloqueioDeLeitoDTO1).isNotEqualTo(motivoDoBloqueioDeLeitoDTO2);
        motivoDoBloqueioDeLeitoDTO1.setId(null);
        assertThat(motivoDoBloqueioDeLeitoDTO1).isNotEqualTo(motivoDoBloqueioDeLeitoDTO2);
    }
}
