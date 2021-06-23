package br.com.basis.madre.exames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class RecomendacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecomendacaoDTO.class);
        RecomendacaoDTO recomendacaoDTO1 = new RecomendacaoDTO();
        recomendacaoDTO1.setId(1L);
        RecomendacaoDTO recomendacaoDTO2 = new RecomendacaoDTO();
        assertThat(recomendacaoDTO1).isNotEqualTo(recomendacaoDTO2);
        recomendacaoDTO2.setId(recomendacaoDTO1.getId());
        assertThat(recomendacaoDTO1).isEqualTo(recomendacaoDTO2);
        recomendacaoDTO2.setId(2L);
        assertThat(recomendacaoDTO1).isNotEqualTo(recomendacaoDTO2);
        recomendacaoDTO1.setId(null);
        assertThat(recomendacaoDTO1).isNotEqualTo(recomendacaoDTO2);
    }
}
