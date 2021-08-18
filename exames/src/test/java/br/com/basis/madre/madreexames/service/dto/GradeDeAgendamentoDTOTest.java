package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class GradeDeAgendamentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GradeDeAgendamentoDTO.class);
        GradeDeAgendamentoDTO gradeDeAgendamentoDTO1 = new GradeDeAgendamentoDTO();
        gradeDeAgendamentoDTO1.setId(1L);
        GradeDeAgendamentoDTO gradeDeAgendamentoDTO2 = new GradeDeAgendamentoDTO();
        assertThat(gradeDeAgendamentoDTO1).isNotEqualTo(gradeDeAgendamentoDTO2);
        gradeDeAgendamentoDTO2.setId(gradeDeAgendamentoDTO1.getId());
        assertThat(gradeDeAgendamentoDTO1).isEqualTo(gradeDeAgendamentoDTO2);
        gradeDeAgendamentoDTO2.setId(2L);
        assertThat(gradeDeAgendamentoDTO1).isNotEqualTo(gradeDeAgendamentoDTO2);
        gradeDeAgendamentoDTO1.setId(null);
        assertThat(gradeDeAgendamentoDTO1).isNotEqualTo(gradeDeAgendamentoDTO2);
    }
}
