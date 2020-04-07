package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class OcupacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ocupacao.class);
        Ocupacao ocupacao1 = new Ocupacao();
        ocupacao1.setId(1L);
        Ocupacao ocupacao2 = new Ocupacao();
        ocupacao2.setId(ocupacao1.getId());
        assertThat(ocupacao1).isEqualTo(ocupacao2);
        ocupacao2.setId(2L);
        assertThat(ocupacao1).isNotEqualTo(ocupacao2);
        ocupacao1.setId(null);
        assertThat(ocupacao1).isNotEqualTo(ocupacao2);
    }
}
