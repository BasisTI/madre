package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class EtniaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtniaDTO.class);
        EtniaDTO etniaDTO1 = new EtniaDTO();
        etniaDTO1.setId(1L);
        EtniaDTO etniaDTO2 = new EtniaDTO();
        assertThat(etniaDTO1).isNotEqualTo(etniaDTO2);
        etniaDTO2.setId(etniaDTO1.getId());
        assertThat(etniaDTO1).isEqualTo(etniaDTO2);
        etniaDTO2.setId(2L);
        assertThat(etniaDTO1).isNotEqualTo(etniaDTO2);
        etniaDTO1.setId(null);
        assertThat(etniaDTO1).isNotEqualTo(etniaDTO2);
    }
}
