package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class EquipeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipeDTO.class);
        EquipeDTO equipeDTO1 = new EquipeDTO();
        equipeDTO1.setId(1L);
        EquipeDTO equipeDTO2 = new EquipeDTO();
        assertThat(equipeDTO1).isNotEqualTo(equipeDTO2);
        equipeDTO2.setId(equipeDTO1.getId());
        assertThat(equipeDTO1).isEqualTo(equipeDTO2);
        equipeDTO2.setId(2L);
        assertThat(equipeDTO1).isNotEqualTo(equipeDTO2);
        equipeDTO1.setId(null);
        assertThat(equipeDTO1).isNotEqualTo(equipeDTO2);
    }
}
