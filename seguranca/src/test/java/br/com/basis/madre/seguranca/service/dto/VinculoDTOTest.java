package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class VinculoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VinculoDTO.class);
        VinculoDTO vinculoDTO1 = new VinculoDTO();
        vinculoDTO1.setId(1L);
        VinculoDTO vinculoDTO2 = new VinculoDTO();
        assertThat(vinculoDTO1).isNotEqualTo(vinculoDTO2);
        vinculoDTO2.setId(vinculoDTO1.getId());
        assertThat(vinculoDTO1).isEqualTo(vinculoDTO2);
        vinculoDTO2.setId(2L);
        assertThat(vinculoDTO1).isNotEqualTo(vinculoDTO2);
        vinculoDTO1.setId(null);
        assertThat(vinculoDTO1).isNotEqualTo(vinculoDTO2);
    }
}
