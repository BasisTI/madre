package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class EtniaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etnia.class);
        Etnia etnia1 = new Etnia();
        etnia1.setId(1L);
        Etnia etnia2 = new Etnia();
        etnia2.setId(etnia1.getId());
        assertThat(etnia1).isEqualTo(etnia2);
        etnia2.setId(2L);
        assertThat(etnia1).isNotEqualTo(etnia2);
        etnia1.setId(null);
        assertThat(etnia1).isNotEqualTo(etnia2);
    }
}
