package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class ExameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exame.class);
        Exame exame1 = new Exame();
        exame1.setId(1L);
        Exame exame2 = new Exame();
        exame2.setId(exame1.getId());
        assertThat(exame1).isEqualTo(exame2);
        exame2.setId(2L);
        assertThat(exame1).isNotEqualTo(exame2);
        exame1.setId(null);
        assertThat(exame1).isNotEqualTo(exame2);
    }
}
