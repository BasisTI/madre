package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class CertidaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Certidao.class);
        Certidao certidao1 = new Certidao();
        certidao1.setId(1L);
        Certidao certidao2 = new Certidao();
        certidao2.setId(certidao1.getId());
        assertThat(certidao1).isEqualTo(certidao2);
        certidao2.setId(2L);
        assertThat(certidao1).isNotEqualTo(certidao2);
        certidao1.setId(null);
        assertThat(certidao1).isNotEqualTo(certidao2);
    }
}
