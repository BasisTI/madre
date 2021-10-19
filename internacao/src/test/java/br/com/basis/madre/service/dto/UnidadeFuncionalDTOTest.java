package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class UnidadeFuncionalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeFuncionalDTO.class);
        UnidadeFuncionalDTO unidadeFuncionalDTO1 = new UnidadeFuncionalDTO();
        unidadeFuncionalDTO1.setId(1L);
        UnidadeFuncionalDTO unidadeFuncionalDTO2 = new UnidadeFuncionalDTO();
        assertThat(unidadeFuncionalDTO1).isNotEqualTo(unidadeFuncionalDTO2);
        unidadeFuncionalDTO2.setId(unidadeFuncionalDTO1.getId());
        assertThat(unidadeFuncionalDTO1).isEqualTo(unidadeFuncionalDTO2);
        unidadeFuncionalDTO2.setId(2L);
        assertThat(unidadeFuncionalDTO1).isNotEqualTo(unidadeFuncionalDTO2);
        unidadeFuncionalDTO1.setId(null);
        assertThat(unidadeFuncionalDTO1).isNotEqualTo(unidadeFuncionalDTO2);
    }
}
