package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class GrupoAgendamentoExameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoAgendamentoExame.class);
        GrupoAgendamentoExame grupoAgendamentoExame1 = new GrupoAgendamentoExame();
        grupoAgendamentoExame1.setId(1L);
        GrupoAgendamentoExame grupoAgendamentoExame2 = new GrupoAgendamentoExame();
        grupoAgendamentoExame2.setId(grupoAgendamentoExame1.getId());
        assertThat(grupoAgendamentoExame1).isEqualTo(grupoAgendamentoExame2);
        grupoAgendamentoExame2.setId(2L);
        assertThat(grupoAgendamentoExame1).isNotEqualTo(grupoAgendamentoExame2);
        grupoAgendamentoExame1.setId(null);
        assertThat(grupoAgendamentoExame1).isNotEqualTo(grupoAgendamentoExame2);
    }
}
