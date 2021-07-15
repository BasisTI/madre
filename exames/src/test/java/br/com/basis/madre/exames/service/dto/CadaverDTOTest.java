package br.com.basis.madre.exames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class CadaverDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CadaverDTO.class);
        CadaverDTO cadaverDTO1 = new CadaverDTO();
        cadaverDTO1.setId(1L);
        CadaverDTO cadaverDTO2 = new CadaverDTO();
        assertThat(cadaverDTO1).isNotEqualTo(cadaverDTO2);
        cadaverDTO2.setId(cadaverDTO1.getId());
        assertThat(cadaverDTO1).isEqualTo(cadaverDTO2);
        cadaverDTO2.setId(2L);
        assertThat(cadaverDTO1).isNotEqualTo(cadaverDTO2);
        cadaverDTO1.setId(null);
        assertThat(cadaverDTO1).isNotEqualTo(cadaverDTO2);
    }
}
