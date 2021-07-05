package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class RecomendacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recomendacao.class);
        Recomendacao recomendacao1 = new Recomendacao();
        recomendacao1.setId(1L);
        Recomendacao recomendacao2 = new Recomendacao();
        recomendacao2.setId(recomendacao1.getId());
        assertThat(recomendacao1).isEqualTo(recomendacao2);
        recomendacao2.setId(2L);
        assertThat(recomendacao1).isNotEqualTo(recomendacao2);
        recomendacao1.setId(null);
        assertThat(recomendacao1).isNotEqualTo(recomendacao2);
    }
}
