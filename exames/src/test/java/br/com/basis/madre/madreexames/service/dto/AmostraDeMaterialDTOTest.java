package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class AmostraDeMaterialDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmostraDeMaterialDTO.class);
        AmostraDeMaterialDTO amostraDeMaterialDTO1 = new AmostraDeMaterialDTO();
        amostraDeMaterialDTO1.setId(1L);
        AmostraDeMaterialDTO amostraDeMaterialDTO2 = new AmostraDeMaterialDTO();
        assertThat(amostraDeMaterialDTO1).isNotEqualTo(amostraDeMaterialDTO2);
        amostraDeMaterialDTO2.setId(amostraDeMaterialDTO1.getId());
        assertThat(amostraDeMaterialDTO1).isEqualTo(amostraDeMaterialDTO2);
        amostraDeMaterialDTO2.setId(2L);
        assertThat(amostraDeMaterialDTO1).isNotEqualTo(amostraDeMaterialDTO2);
        amostraDeMaterialDTO1.setId(null);
        assertThat(amostraDeMaterialDTO1).isNotEqualTo(amostraDeMaterialDTO2);
    }
}
