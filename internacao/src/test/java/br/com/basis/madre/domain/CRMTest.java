package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class CRMTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRM.class);
        CRM cRM1 = new CRM();
        cRM1.setId(1L);
        CRM cRM2 = new CRM();
        cRM2.setId(cRM1.getId());
        assertThat(cRM1).isEqualTo(cRM2);
        cRM2.setId(2L);
        assertThat(cRM1).isNotEqualTo(cRM2);
        cRM1.setId(null);
        assertThat(cRM1).isNotEqualTo(cRM2);
    }
}
