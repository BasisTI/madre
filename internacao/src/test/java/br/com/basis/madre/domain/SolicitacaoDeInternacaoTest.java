package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class SolicitacaoDeInternacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitacaoDeInternacao.class);
        SolicitacaoDeInternacao solicitacaoDeInternacao1 = new SolicitacaoDeInternacao();
        solicitacaoDeInternacao1.setId(1L);
        SolicitacaoDeInternacao solicitacaoDeInternacao2 = new SolicitacaoDeInternacao();
        solicitacaoDeInternacao2.setId(solicitacaoDeInternacao1.getId());
        assertThat(solicitacaoDeInternacao1).isEqualTo(solicitacaoDeInternacao2);
        solicitacaoDeInternacao2.setId(2L);
        assertThat(solicitacaoDeInternacao1).isNotEqualTo(solicitacaoDeInternacao2);
        solicitacaoDeInternacao1.setId(null);
        assertThat(solicitacaoDeInternacao1).isNotEqualTo(solicitacaoDeInternacao2);
    }
}
