package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class RecipienteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipiente.class);
        Recipiente recipiente1 = new Recipiente();
        recipiente1.setId(1L);
        Recipiente recipiente2 = new Recipiente();
        recipiente2.setId(recipiente1.getId());
        assertThat(recipiente1).isEqualTo(recipiente2);
        recipiente2.setId(2L);
        assertThat(recipiente1).isNotEqualTo(recipiente2);
        recipiente1.setId(null);
        assertThat(recipiente1).isNotEqualTo(recipiente2);
    }
}
