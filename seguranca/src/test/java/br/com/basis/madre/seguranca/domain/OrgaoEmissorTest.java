package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class OrgaoEmissorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgaoEmissor.class);
        OrgaoEmissor orgaoEmissor1 = new OrgaoEmissor();
        orgaoEmissor1.setId(1L);
        OrgaoEmissor orgaoEmissor2 = new OrgaoEmissor();
        orgaoEmissor2.setId(orgaoEmissor1.getId());
        assertThat(orgaoEmissor1).isEqualTo(orgaoEmissor2);
        orgaoEmissor2.setId(2L);
        assertThat(orgaoEmissor1).isNotEqualTo(orgaoEmissor2);
        orgaoEmissor1.setId(null);
        assertThat(orgaoEmissor1).isNotEqualTo(orgaoEmissor2);
    }
}
