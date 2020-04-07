package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ReligiaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Religiao.class);
        Religiao religiao1 = new Religiao();
        religiao1.setId(1L);
        Religiao religiao2 = new Religiao();
        religiao2.setId(religiao1.getId());
        assertThat(religiao1).isEqualTo(religiao2);
        religiao2.setId(2L);
        assertThat(religiao1).isNotEqualTo(religiao2);
        religiao1.setId(null);
        assertThat(religiao1).isNotEqualTo(religiao2);
    }
}
