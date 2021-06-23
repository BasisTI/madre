package br.com.basis.madre.exames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class LaboratorioExternoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LaboratorioExternoDTO.class);
        LaboratorioExternoDTO laboratorioExternoDTO1 = new LaboratorioExternoDTO();
        laboratorioExternoDTO1.setId(1L);
        LaboratorioExternoDTO laboratorioExternoDTO2 = new LaboratorioExternoDTO();
        assertThat(laboratorioExternoDTO1).isNotEqualTo(laboratorioExternoDTO2);
        laboratorioExternoDTO2.setId(laboratorioExternoDTO1.getId());
        assertThat(laboratorioExternoDTO1).isEqualTo(laboratorioExternoDTO2);
        laboratorioExternoDTO2.setId(2L);
        assertThat(laboratorioExternoDTO1).isNotEqualTo(laboratorioExternoDTO2);
        laboratorioExternoDTO1.setId(null);
        assertThat(laboratorioExternoDTO1).isNotEqualTo(laboratorioExternoDTO2);
    }
}
