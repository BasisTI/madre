package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class HorarioExameDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioExameDTO.class);
        HorarioExameDTO horarioExameDTO1 = new HorarioExameDTO();
        horarioExameDTO1.setId(1L);
        HorarioExameDTO horarioExameDTO2 = new HorarioExameDTO();
        assertThat(horarioExameDTO1).isNotEqualTo(horarioExameDTO2);
        horarioExameDTO2.setId(horarioExameDTO1.getId());
        assertThat(horarioExameDTO1).isEqualTo(horarioExameDTO2);
        horarioExameDTO2.setId(2L);
        assertThat(horarioExameDTO1).isNotEqualTo(horarioExameDTO2);
        horarioExameDTO1.setId(null);
        assertThat(horarioExameDTO1).isNotEqualTo(horarioExameDTO2);
    }
}
