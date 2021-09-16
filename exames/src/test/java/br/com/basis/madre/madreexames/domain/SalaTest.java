package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class SalaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sala.class);
        Sala sala1 = new Sala();
        sala1.setId(1L);
        Sala sala2 = new Sala();
        sala2.setId(sala1.getId());
        assertThat(sala1).isEqualTo(sala2);
        sala2.setId(2L);
        assertThat(sala1).isNotEqualTo(sala2);
        sala1.setId(null);
        assertThat(sala1).isNotEqualTo(sala2);
    }
}
