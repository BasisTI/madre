package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class SolicitacaoExameDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitacaoExameDTO.class);
        SolicitacaoExameDTO solicitacaoExameDTO1 = new SolicitacaoExameDTO();
        solicitacaoExameDTO1.setId(1L);
        SolicitacaoExameDTO solicitacaoExameDTO2 = new SolicitacaoExameDTO();
        assertThat(solicitacaoExameDTO1).isNotEqualTo(solicitacaoExameDTO2);
        solicitacaoExameDTO2.setId(solicitacaoExameDTO1.getId());
        assertThat(solicitacaoExameDTO1).isEqualTo(solicitacaoExameDTO2);
        solicitacaoExameDTO2.setId(2L);
        assertThat(solicitacaoExameDTO1).isNotEqualTo(solicitacaoExameDTO2);
        solicitacaoExameDTO1.setId(null);
        assertThat(solicitacaoExameDTO1).isNotEqualTo(solicitacaoExameDTO2);
    }
}
