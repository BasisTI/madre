package br.com.basis.suprimentos.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class SituacaoRequisicaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituacaoRequisicao.class);
        SituacaoRequisicao situacaoRequisicao1 = new SituacaoRequisicao();
        situacaoRequisicao1.setId(1L);
        SituacaoRequisicao situacaoRequisicao2 = new SituacaoRequisicao();
        situacaoRequisicao2.setId(situacaoRequisicao1.getId());
        assertThat(situacaoRequisicao1).isEqualTo(situacaoRequisicao2);
        situacaoRequisicao2.setId(2L);
        assertThat(situacaoRequisicao1).isNotEqualTo(situacaoRequisicao2);
        situacaoRequisicao1.setId(null);
        assertThat(situacaoRequisicao1).isNotEqualTo(situacaoRequisicao2);
    }
}
