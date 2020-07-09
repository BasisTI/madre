package br.com.basis.suprimentos.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class SituacaoRequisicaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituacaoRequisicaoDTO.class);
        SituacaoRequisicaoDTO situacaoRequisicaoDTO1 = new SituacaoRequisicaoDTO();
        situacaoRequisicaoDTO1.setId(1L);
        SituacaoRequisicaoDTO situacaoRequisicaoDTO2 = new SituacaoRequisicaoDTO();
        assertThat(situacaoRequisicaoDTO1).isNotEqualTo(situacaoRequisicaoDTO2);
        situacaoRequisicaoDTO2.setId(situacaoRequisicaoDTO1.getId());
        assertThat(situacaoRequisicaoDTO1).isEqualTo(situacaoRequisicaoDTO2);
        situacaoRequisicaoDTO2.setId(2L);
        assertThat(situacaoRequisicaoDTO1).isNotEqualTo(situacaoRequisicaoDTO2);
        situacaoRequisicaoDTO1.setId(null);
        assertThat(situacaoRequisicaoDTO1).isNotEqualTo(situacaoRequisicaoDTO2);
    }
}
