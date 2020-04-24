package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class RacaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RacaDTO.class);
        RacaDTO racaDTO1 = new RacaDTO();
        racaDTO1.setId(1L);
        RacaDTO racaDTO2 = new RacaDTO();
        assertThat(racaDTO1).isNotEqualTo(racaDTO2);
        racaDTO2.setId(racaDTO1.getId());
        assertThat(racaDTO1).isEqualTo(racaDTO2);
        racaDTO2.setId(2L);
        assertThat(racaDTO1).isNotEqualTo(racaDTO2);
        racaDTO1.setId(null);
        assertThat(racaDTO1).isNotEqualTo(racaDTO2);
    }
}
