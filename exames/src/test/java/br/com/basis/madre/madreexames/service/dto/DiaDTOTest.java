package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class DiaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiaDTO.class);
        DiaDTO diaDTO1 = new DiaDTO();
        diaDTO1.setId(1L);
        DiaDTO diaDTO2 = new DiaDTO();
        assertThat(diaDTO1).isNotEqualTo(diaDTO2);
        diaDTO2.setId(diaDTO1.getId());
        assertThat(diaDTO1).isEqualTo(diaDTO2);
        diaDTO2.setId(2L);
        assertThat(diaDTO1).isNotEqualTo(diaDTO2);
        diaDTO1.setId(null);
        assertThat(diaDTO1).isNotEqualTo(diaDTO2);
    }
}
