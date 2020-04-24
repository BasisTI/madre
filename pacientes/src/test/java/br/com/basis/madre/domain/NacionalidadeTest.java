package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class NacionalidadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nacionalidade.class);
        Nacionalidade nacionalidade1 = new Nacionalidade();
        nacionalidade1.setId(1L);
        Nacionalidade nacionalidade2 = new Nacionalidade();
        nacionalidade2.setId(nacionalidade1.getId());
        assertThat(nacionalidade1).isEqualTo(nacionalidade2);
        nacionalidade2.setId(2L);
        assertThat(nacionalidade1).isNotEqualTo(nacionalidade2);
        nacionalidade1.setId(null);
        assertThat(nacionalidade1).isNotEqualTo(nacionalidade2);
    }
}
