package br.com.basis.madre.exames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class SolicitacaoExameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitacaoExame.class);
        SolicitacaoExame solicitacaoExame1 = new SolicitacaoExame();
        solicitacaoExame1.setId(1L);
        SolicitacaoExame solicitacaoExame2 = new SolicitacaoExame();
        solicitacaoExame2.setId(solicitacaoExame1.getId());
        assertThat(solicitacaoExame1).isEqualTo(solicitacaoExame2);
        solicitacaoExame2.setId(2L);
        assertThat(solicitacaoExame1).isNotEqualTo(solicitacaoExame2);
        solicitacaoExame1.setId(null);
        assertThat(solicitacaoExame1).isNotEqualTo(solicitacaoExame2);
    }
}
