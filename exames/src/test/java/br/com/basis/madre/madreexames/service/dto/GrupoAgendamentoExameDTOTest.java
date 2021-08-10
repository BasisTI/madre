package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class GrupoAgendamentoExameDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoAgendamentoExameDTO.class);
        GrupoAgendamentoExameDTO grupoAgendamentoExameDTO1 = new GrupoAgendamentoExameDTO();
        grupoAgendamentoExameDTO1.setId(1L);
        GrupoAgendamentoExameDTO grupoAgendamentoExameDTO2 = new GrupoAgendamentoExameDTO();
        assertThat(grupoAgendamentoExameDTO1).isNotEqualTo(grupoAgendamentoExameDTO2);
        grupoAgendamentoExameDTO2.setId(grupoAgendamentoExameDTO1.getId());
        assertThat(grupoAgendamentoExameDTO1).isEqualTo(grupoAgendamentoExameDTO2);
        grupoAgendamentoExameDTO2.setId(2L);
        assertThat(grupoAgendamentoExameDTO1).isNotEqualTo(grupoAgendamentoExameDTO2);
        grupoAgendamentoExameDTO1.setId(null);
        assertThat(grupoAgendamentoExameDTO1).isNotEqualTo(grupoAgendamentoExameDTO2);
    }
}
