package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class UFTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UF.class);
        UF uF1 = new UF();
        uF1.setId(1L);
        UF uF2 = new UF();
        uF2.setId(uF1.getId());
        assertThat(uF1).isEqualTo(uF2);
        uF2.setId(2L);
        assertThat(uF1).isNotEqualTo(uF2);
        uF1.setId(null);
        assertThat(uF1).isNotEqualTo(uF2);
    }
}
