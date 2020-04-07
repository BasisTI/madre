package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class NaturalidadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Naturalidade.class);
        Naturalidade naturalidade1 = new Naturalidade();
        naturalidade1.setId(1L);
        Naturalidade naturalidade2 = new Naturalidade();
        naturalidade2.setId(naturalidade1.getId());
        assertThat(naturalidade1).isEqualTo(naturalidade2);
        naturalidade2.setId(2L);
        assertThat(naturalidade1).isNotEqualTo(naturalidade2);
        naturalidade1.setId(null);
        assertThat(naturalidade1).isNotEqualTo(naturalidade2);
    }
}
