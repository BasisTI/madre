package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class HorarioLivreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioLivre.class);
        HorarioLivre horarioLivre1 = new HorarioLivre();
        horarioLivre1.setId(1L);
        HorarioLivre horarioLivre2 = new HorarioLivre();
        horarioLivre2.setId(horarioLivre1.getId());
        assertThat(horarioLivre1).isEqualTo(horarioLivre2);
        horarioLivre2.setId(2L);
        assertThat(horarioLivre1).isNotEqualTo(horarioLivre2);
        horarioLivre1.setId(null);
        assertThat(horarioLivre1).isNotEqualTo(horarioLivre2);
    }
}
