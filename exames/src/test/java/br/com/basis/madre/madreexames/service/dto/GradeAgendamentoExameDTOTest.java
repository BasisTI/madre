package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class GradeAgendamentoExameDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GradeAgendamentoExameDTO.class);
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO1 = new GradeAgendamentoExameDTO();
        gradeAgendamentoExameDTO1.setId(1L);
        GradeAgendamentoExameDTO gradeAgendamentoExameDTO2 = new GradeAgendamentoExameDTO();
        assertThat(gradeAgendamentoExameDTO1).isNotEqualTo(gradeAgendamentoExameDTO2);
        gradeAgendamentoExameDTO2.setId(gradeAgendamentoExameDTO1.getId());
        assertThat(gradeAgendamentoExameDTO1).isEqualTo(gradeAgendamentoExameDTO2);
        gradeAgendamentoExameDTO2.setId(2L);
        assertThat(gradeAgendamentoExameDTO1).isNotEqualTo(gradeAgendamentoExameDTO2);
        gradeAgendamentoExameDTO1.setId(null);
        assertThat(gradeAgendamentoExameDTO1).isNotEqualTo(gradeAgendamentoExameDTO2);
    }
}
