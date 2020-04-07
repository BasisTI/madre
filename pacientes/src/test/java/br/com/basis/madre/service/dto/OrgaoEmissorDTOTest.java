package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class OrgaoEmissorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgaoEmissorDTO.class);
        OrgaoEmissorDTO orgaoEmissorDTO1 = new OrgaoEmissorDTO();
        orgaoEmissorDTO1.setId(1L);
        OrgaoEmissorDTO orgaoEmissorDTO2 = new OrgaoEmissorDTO();
        assertThat(orgaoEmissorDTO1).isNotEqualTo(orgaoEmissorDTO2);
        orgaoEmissorDTO2.setId(orgaoEmissorDTO1.getId());
        assertThat(orgaoEmissorDTO1).isEqualTo(orgaoEmissorDTO2);
        orgaoEmissorDTO2.setId(2L);
        assertThat(orgaoEmissorDTO1).isNotEqualTo(orgaoEmissorDTO2);
        orgaoEmissorDTO1.setId(null);
        assertThat(orgaoEmissorDTO1).isNotEqualTo(orgaoEmissorDTO2);
    }
}
