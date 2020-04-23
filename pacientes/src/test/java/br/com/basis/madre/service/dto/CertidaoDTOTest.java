package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class CertidaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertidaoDTO.class);
        CertidaoDTO certidaoDTO1 = new CertidaoDTO();
        certidaoDTO1.setId(1L);
        CertidaoDTO certidaoDTO2 = new CertidaoDTO();
        assertThat(certidaoDTO1).isNotEqualTo(certidaoDTO2);
        certidaoDTO2.setId(certidaoDTO1.getId());
        assertThat(certidaoDTO1).isEqualTo(certidaoDTO2);
        certidaoDTO2.setId(2L);
        assertThat(certidaoDTO1).isNotEqualTo(certidaoDTO2);
        certidaoDTO1.setId(null);
        assertThat(certidaoDTO1).isNotEqualTo(certidaoDTO2);
    }
}
