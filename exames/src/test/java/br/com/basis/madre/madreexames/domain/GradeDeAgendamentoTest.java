package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class GradeDeAgendamentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GradeDeAgendamento.class);
        GradeDeAgendamento gradeDeAgendamento1 = new GradeDeAgendamento();
        gradeDeAgendamento1.setId(1L);
        GradeDeAgendamento gradeDeAgendamento2 = new GradeDeAgendamento();
        gradeDeAgendamento2.setId(gradeDeAgendamento1.getId());
        assertThat(gradeDeAgendamento1).isEqualTo(gradeDeAgendamento2);
        gradeDeAgendamento2.setId(2L);
        assertThat(gradeDeAgendamento1).isNotEqualTo(gradeDeAgendamento2);
        gradeDeAgendamento1.setId(null);
        assertThat(gradeDeAgendamento1).isNotEqualTo(gradeDeAgendamento2);
    }
}
