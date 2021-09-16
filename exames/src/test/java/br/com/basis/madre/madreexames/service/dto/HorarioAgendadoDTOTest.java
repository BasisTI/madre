package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class HorarioAgendadoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioAgendadoDTO.class);
        HorarioAgendadoDTO horarioAgendadoDTO1 = new HorarioAgendadoDTO();
        horarioAgendadoDTO1.setId(1L);
        HorarioAgendadoDTO horarioAgendadoDTO2 = new HorarioAgendadoDTO();
        assertThat(horarioAgendadoDTO1).isNotEqualTo(horarioAgendadoDTO2);
        horarioAgendadoDTO2.setId(horarioAgendadoDTO1.getId());
        assertThat(horarioAgendadoDTO1).isEqualTo(horarioAgendadoDTO2);
        horarioAgendadoDTO2.setId(2L);
        assertThat(horarioAgendadoDTO1).isNotEqualTo(horarioAgendadoDTO2);
        horarioAgendadoDTO1.setId(null);
        assertThat(horarioAgendadoDTO1).isNotEqualTo(horarioAgendadoDTO2);
    }
}
