package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class AnticoagulanteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnticoagulanteDTO.class);
        AnticoagulanteDTO anticoagulanteDTO1 = new AnticoagulanteDTO();
        anticoagulanteDTO1.setId(1L);
        AnticoagulanteDTO anticoagulanteDTO2 = new AnticoagulanteDTO();
        assertThat(anticoagulanteDTO1).isNotEqualTo(anticoagulanteDTO2);
        anticoagulanteDTO2.setId(anticoagulanteDTO1.getId());
        assertThat(anticoagulanteDTO1).isEqualTo(anticoagulanteDTO2);
        anticoagulanteDTO2.setId(2L);
        assertThat(anticoagulanteDTO1).isNotEqualTo(anticoagulanteDTO2);
        anticoagulanteDTO1.setId(null);
        assertThat(anticoagulanteDTO1).isNotEqualTo(anticoagulanteDTO2);
    }
}
