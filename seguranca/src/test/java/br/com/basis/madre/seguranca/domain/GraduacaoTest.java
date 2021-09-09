package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class GraduacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Graduacao.class);
        Graduacao graduacao1 = new Graduacao();
        graduacao1.setId(1L);
        Graduacao graduacao2 = new Graduacao();
        graduacao2.setId(graduacao1.getId());
        assertThat(graduacao1).isEqualTo(graduacao2);
        graduacao2.setId(2L);
        assertThat(graduacao1).isNotEqualTo(graduacao2);
        graduacao1.setId(null);
        assertThat(graduacao1).isNotEqualTo(graduacao2);
    }
}
