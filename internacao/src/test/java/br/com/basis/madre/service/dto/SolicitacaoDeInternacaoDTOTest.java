package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class SolicitacaoDeInternacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitacaoDeInternacaoDTO.class);
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO1 = new SolicitacaoDeInternacaoDTO();
        solicitacaoDeInternacaoDTO1.setId(1L);
        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO2 = new SolicitacaoDeInternacaoDTO();
        assertThat(solicitacaoDeInternacaoDTO1).isNotEqualTo(solicitacaoDeInternacaoDTO2);
        solicitacaoDeInternacaoDTO2.setId(solicitacaoDeInternacaoDTO1.getId());
        assertThat(solicitacaoDeInternacaoDTO1).isEqualTo(solicitacaoDeInternacaoDTO2);
        solicitacaoDeInternacaoDTO2.setId(2L);
        assertThat(solicitacaoDeInternacaoDTO1).isNotEqualTo(solicitacaoDeInternacaoDTO2);
        solicitacaoDeInternacaoDTO1.setId(null);
        assertThat(solicitacaoDeInternacaoDTO1).isNotEqualTo(solicitacaoDeInternacaoDTO2);
    }
}
