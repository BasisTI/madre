package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class VinculoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vinculo.class);
        Vinculo vinculo1 = new Vinculo();
        vinculo1.setId(1L);
        Vinculo vinculo2 = new Vinculo();
        vinculo2.setId(vinculo1.getId());
        assertThat(vinculo1).isEqualTo(vinculo2);
        vinculo2.setId(2L);
        assertThat(vinculo1).isNotEqualTo(vinculo2);
        vinculo1.setId(null);
        assertThat(vinculo1).isNotEqualTo(vinculo2);
    }
}
