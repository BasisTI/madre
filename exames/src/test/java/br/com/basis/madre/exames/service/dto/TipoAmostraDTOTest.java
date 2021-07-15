package br.com.basis.madre.exames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class TipoAmostraDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoAmostraDTO.class);
        TipoAmostraDTO tipoAmostraDTO1 = new TipoAmostraDTO();
        tipoAmostraDTO1.setId(1L);
        TipoAmostraDTO tipoAmostraDTO2 = new TipoAmostraDTO();
        assertThat(tipoAmostraDTO1).isNotEqualTo(tipoAmostraDTO2);
        tipoAmostraDTO2.setId(tipoAmostraDTO1.getId());
        assertThat(tipoAmostraDTO1).isEqualTo(tipoAmostraDTO2);
        tipoAmostraDTO2.setId(2L);
        assertThat(tipoAmostraDTO1).isNotEqualTo(tipoAmostraDTO2);
        tipoAmostraDTO1.setId(null);
        assertThat(tipoAmostraDTO1).isNotEqualTo(tipoAmostraDTO2);
    }
}
