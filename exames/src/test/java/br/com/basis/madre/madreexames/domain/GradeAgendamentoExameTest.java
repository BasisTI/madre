package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class GradeAgendamentoExameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GradeAgendamentoExame.class);
        GradeAgendamentoExame gradeAgendamentoExame1 = new GradeAgendamentoExame();
        gradeAgendamentoExame1.setId(1L);
        GradeAgendamentoExame gradeAgendamentoExame2 = new GradeAgendamentoExame();
        gradeAgendamentoExame2.setId(gradeAgendamentoExame1.getId());
        assertThat(gradeAgendamentoExame1).isEqualTo(gradeAgendamentoExame2);
        gradeAgendamentoExame2.setId(2L);
        assertThat(gradeAgendamentoExame1).isNotEqualTo(gradeAgendamentoExame2);
        gradeAgendamentoExame1.setId(null);
        assertThat(gradeAgendamentoExame1).isNotEqualTo(gradeAgendamentoExame2);
    }
}
