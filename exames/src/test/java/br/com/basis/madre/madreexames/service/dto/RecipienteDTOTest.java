package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class RecipienteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipienteDTO.class);
        RecipienteDTO recipienteDTO1 = new RecipienteDTO();
        recipienteDTO1.setId(1L);
        RecipienteDTO recipienteDTO2 = new RecipienteDTO();
        assertThat(recipienteDTO1).isNotEqualTo(recipienteDTO2);
        recipienteDTO2.setId(recipienteDTO1.getId());
        assertThat(recipienteDTO1).isEqualTo(recipienteDTO2);
        recipienteDTO2.setId(2L);
        assertThat(recipienteDTO1).isNotEqualTo(recipienteDTO2);
        recipienteDTO1.setId(null);
        assertThat(recipienteDTO1).isNotEqualTo(recipienteDTO2);
    }
}
