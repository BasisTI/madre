package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class ServidorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServidorDTO.class);
        ServidorDTO servidorDTO1 = new ServidorDTO();
        servidorDTO1.setId(1L);
        ServidorDTO servidorDTO2 = new ServidorDTO();
        assertThat(servidorDTO1).isNotEqualTo(servidorDTO2);
        servidorDTO2.setId(servidorDTO1.getId());
        assertThat(servidorDTO1).isEqualTo(servidorDTO2);
        servidorDTO2.setId(2L);
        assertThat(servidorDTO1).isNotEqualTo(servidorDTO2);
        servidorDTO1.setId(null);
        assertThat(servidorDTO1).isNotEqualTo(servidorDTO2);
    }
}
