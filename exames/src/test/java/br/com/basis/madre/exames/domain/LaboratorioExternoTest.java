package br.com.basis.madre.exames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class LaboratorioExternoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LaboratorioExterno.class);
        LaboratorioExterno laboratorioExterno1 = new LaboratorioExterno();
        laboratorioExterno1.setId(1L);
        LaboratorioExterno laboratorioExterno2 = new LaboratorioExterno();
        laboratorioExterno2.setId(laboratorioExterno1.getId());
        assertThat(laboratorioExterno1).isEqualTo(laboratorioExterno2);
        laboratorioExterno2.setId(2L);
        assertThat(laboratorioExterno1).isNotEqualTo(laboratorioExterno2);
        laboratorioExterno1.setId(null);
        assertThat(laboratorioExterno1).isNotEqualTo(laboratorioExterno2);
    }
}
