package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class HorarioLivreDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioLivreDTO.class);
        HorarioLivreDTO horarioLivreDTO1 = new HorarioLivreDTO();
        horarioLivreDTO1.setId(1L);
        HorarioLivreDTO horarioLivreDTO2 = new HorarioLivreDTO();
        assertThat(horarioLivreDTO1).isNotEqualTo(horarioLivreDTO2);
        horarioLivreDTO2.setId(horarioLivreDTO1.getId());
        assertThat(horarioLivreDTO1).isEqualTo(horarioLivreDTO2);
        horarioLivreDTO2.setId(2L);
        assertThat(horarioLivreDTO1).isNotEqualTo(horarioLivreDTO2);
        horarioLivreDTO1.setId(null);
        assertThat(horarioLivreDTO1).isNotEqualTo(horarioLivreDTO2);
    }
}
