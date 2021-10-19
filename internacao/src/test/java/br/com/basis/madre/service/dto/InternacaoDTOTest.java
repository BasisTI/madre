package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class InternacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternacaoDTO.class);
        InternacaoDTO internacaoDTO1 = new InternacaoDTO();
        internacaoDTO1.setId(1L);
        InternacaoDTO internacaoDTO2 = new InternacaoDTO();
        assertThat(internacaoDTO1).isNotEqualTo(internacaoDTO2);
        internacaoDTO2.setId(internacaoDTO1.getId());
        assertThat(internacaoDTO1).isEqualTo(internacaoDTO2);
        internacaoDTO2.setId(2L);
        assertThat(internacaoDTO1).isNotEqualTo(internacaoDTO2);
        internacaoDTO1.setId(null);
        assertThat(internacaoDTO1).isNotEqualTo(internacaoDTO2);
    }
}
