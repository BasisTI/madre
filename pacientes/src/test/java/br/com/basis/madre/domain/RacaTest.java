package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class RacaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Raca.class);
        Raca raca1 = new Raca();
        raca1.setId(1L);
        Raca raca2 = new Raca();
        raca2.setId(raca1.getId());
        assertThat(raca1).isEqualTo(raca2);
        raca2.setId(2L);
        assertThat(raca1).isNotEqualTo(raca2);
        raca1.setId(null);
        assertThat(raca1).isNotEqualTo(raca2);
    }
}
