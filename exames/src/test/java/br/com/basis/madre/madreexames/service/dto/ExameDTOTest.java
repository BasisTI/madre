package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class ExameDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExameDTO.class);
        ExameDTO exameDTO1 = new ExameDTO();
        exameDTO1.setId(1L);
        ExameDTO exameDTO2 = new ExameDTO();
        assertThat(exameDTO1).isNotEqualTo(exameDTO2);
        exameDTO2.setId(exameDTO1.getId());
        assertThat(exameDTO1).isEqualTo(exameDTO2);
        exameDTO2.setId(2L);
        assertThat(exameDTO1).isNotEqualTo(exameDTO2);
        exameDTO1.setId(null);
        assertThat(exameDTO1).isNotEqualTo(exameDTO2);
    }
}
