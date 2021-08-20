package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class InstituicaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Instituicao.class);
        Instituicao instituicao1 = new Instituicao();
        instituicao1.setId(1L);
        Instituicao instituicao2 = new Instituicao();
        instituicao2.setId(instituicao1.getId());
        assertThat(instituicao1).isEqualTo(instituicao2);
        instituicao2.setId(2L);
        assertThat(instituicao1).isNotEqualTo(instituicao2);
        instituicao1.setId(null);
        assertThat(instituicao1).isNotEqualTo(instituicao2);
    }
}
