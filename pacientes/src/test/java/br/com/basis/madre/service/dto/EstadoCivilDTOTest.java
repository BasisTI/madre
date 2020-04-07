package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class EstadoCivilDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoCivilDTO.class);
        EstadoCivilDTO estadoCivilDTO1 = new EstadoCivilDTO();
        estadoCivilDTO1.setId(1L);
        EstadoCivilDTO estadoCivilDTO2 = new EstadoCivilDTO();
        assertThat(estadoCivilDTO1).isNotEqualTo(estadoCivilDTO2);
        estadoCivilDTO2.setId(estadoCivilDTO1.getId());
        assertThat(estadoCivilDTO1).isEqualTo(estadoCivilDTO2);
        estadoCivilDTO2.setId(2L);
        assertThat(estadoCivilDTO1).isNotEqualTo(estadoCivilDTO2);
        estadoCivilDTO1.setId(null);
        assertThat(estadoCivilDTO1).isNotEqualTo(estadoCivilDTO2);
    }
}
