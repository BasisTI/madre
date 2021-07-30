package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class GrupoFuncionalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoFuncionalDTO.class);
        GrupoFuncionalDTO grupoFuncionalDTO1 = new GrupoFuncionalDTO();
        grupoFuncionalDTO1.setId(1L);
        GrupoFuncionalDTO grupoFuncionalDTO2 = new GrupoFuncionalDTO();
        assertThat(grupoFuncionalDTO1).isNotEqualTo(grupoFuncionalDTO2);
        grupoFuncionalDTO2.setId(grupoFuncionalDTO1.getId());
        assertThat(grupoFuncionalDTO1).isEqualTo(grupoFuncionalDTO2);
        grupoFuncionalDTO2.setId(2L);
        assertThat(grupoFuncionalDTO1).isNotEqualTo(grupoFuncionalDTO2);
        grupoFuncionalDTO1.setId(null);
        assertThat(grupoFuncionalDTO1).isNotEqualTo(grupoFuncionalDTO2);
    }
}
