package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class OcupacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OcupacaoDTO.class);
        OcupacaoDTO ocupacaoDTO1 = new OcupacaoDTO();
        ocupacaoDTO1.setId(1L);
        OcupacaoDTO ocupacaoDTO2 = new OcupacaoDTO();
        assertThat(ocupacaoDTO1).isNotEqualTo(ocupacaoDTO2);
        ocupacaoDTO2.setId(ocupacaoDTO1.getId());
        assertThat(ocupacaoDTO1).isEqualTo(ocupacaoDTO2);
        ocupacaoDTO2.setId(2L);
        assertThat(ocupacaoDTO1).isNotEqualTo(ocupacaoDTO2);
        ocupacaoDTO1.setId(null);
        assertThat(ocupacaoDTO1).isNotEqualTo(ocupacaoDTO2);
    }
}
