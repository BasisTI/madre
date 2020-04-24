package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class GenitoresTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Genitores.class);
        Genitores genitores1 = new Genitores();
        genitores1.setId(1L);
        Genitores genitores2 = new Genitores();
        genitores2.setId(genitores1.getId());
        assertThat(genitores1).isEqualTo(genitores2);
        genitores2.setId(2L);
        assertThat(genitores1).isNotEqualTo(genitores2);
        genitores1.setId(null);
        assertThat(genitores1).isNotEqualTo(genitores2);
    }
}
