package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class TipoDoEventoLeitoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDoEventoLeitoDTO.class);
        TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO1 = new TipoDoEventoLeitoDTO();
        tipoDoEventoLeitoDTO1.setId(1L);
        TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO2 = new TipoDoEventoLeitoDTO();
        assertThat(tipoDoEventoLeitoDTO1).isNotEqualTo(tipoDoEventoLeitoDTO2);
        tipoDoEventoLeitoDTO2.setId(tipoDoEventoLeitoDTO1.getId());
        assertThat(tipoDoEventoLeitoDTO1).isEqualTo(tipoDoEventoLeitoDTO2);
        tipoDoEventoLeitoDTO2.setId(2L);
        assertThat(tipoDoEventoLeitoDTO1).isNotEqualTo(tipoDoEventoLeitoDTO2);
        tipoDoEventoLeitoDTO1.setId(null);
        assertThat(tipoDoEventoLeitoDTO1).isNotEqualTo(tipoDoEventoLeitoDTO2);
    }
}
