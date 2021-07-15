package br.com.basis.madre.exames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class ControleQualidadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControleQualidade.class);
        ControleQualidade controleQualidade1 = new ControleQualidade();
        controleQualidade1.setId(1L);
        ControleQualidade controleQualidade2 = new ControleQualidade();
        controleQualidade2.setId(controleQualidade1.getId());
        assertThat(controleQualidade1).isEqualTo(controleQualidade2);
        controleQualidade2.setId(2L);
        assertThat(controleQualidade1).isNotEqualTo(controleQualidade2);
        controleQualidade1.setId(null);
        assertThat(controleQualidade1).isNotEqualTo(controleQualidade2);
    }
}
