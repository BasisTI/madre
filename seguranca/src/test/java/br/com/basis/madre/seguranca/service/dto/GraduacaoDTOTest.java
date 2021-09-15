package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class GraduacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GraduacaoDTO.class);
        GraduacaoDTO graduacaoDTO1 = new GraduacaoDTO();
        graduacaoDTO1.setId(1L);
        GraduacaoDTO graduacaoDTO2 = new GraduacaoDTO();
        assertThat(graduacaoDTO1).isNotEqualTo(graduacaoDTO2);
        graduacaoDTO2.setId(graduacaoDTO1.getId());
        assertThat(graduacaoDTO1).isEqualTo(graduacaoDTO2);
        graduacaoDTO2.setId(2L);
        assertThat(graduacaoDTO1).isNotEqualTo(graduacaoDTO2);
        graduacaoDTO1.setId(null);
        assertThat(graduacaoDTO1).isNotEqualTo(graduacaoDTO2);
    }
}
