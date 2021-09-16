package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class MaterialDeExameDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialDeExameDTO.class);
        MaterialDeExameDTO materialDeExameDTO1 = new MaterialDeExameDTO();
        materialDeExameDTO1.setId(1L);
        MaterialDeExameDTO materialDeExameDTO2 = new MaterialDeExameDTO();
        assertThat(materialDeExameDTO1).isNotEqualTo(materialDeExameDTO2);
        materialDeExameDTO2.setId(materialDeExameDTO1.getId());
        assertThat(materialDeExameDTO1).isEqualTo(materialDeExameDTO2);
        materialDeExameDTO2.setId(2L);
        assertThat(materialDeExameDTO1).isNotEqualTo(materialDeExameDTO2);
        materialDeExameDTO1.setId(null);
        assertThat(materialDeExameDTO1).isNotEqualTo(materialDeExameDTO2);
    }
}
