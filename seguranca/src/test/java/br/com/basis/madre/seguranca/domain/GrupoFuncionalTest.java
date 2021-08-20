package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class GrupoFuncionalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoFuncional.class);
        GrupoFuncional grupoFuncional1 = new GrupoFuncional();
        grupoFuncional1.setId(1L);
        GrupoFuncional grupoFuncional2 = new GrupoFuncional();
        grupoFuncional2.setId(grupoFuncional1.getId());
        assertThat(grupoFuncional1).isEqualTo(grupoFuncional2);
        grupoFuncional2.setId(2L);
        assertThat(grupoFuncional1).isNotEqualTo(grupoFuncional2);
        grupoFuncional1.setId(null);
        assertThat(grupoFuncional1).isNotEqualTo(grupoFuncional2);
    }
}
