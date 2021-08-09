package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class TelefoneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telefone.class);
        Telefone telefone1 = new Telefone();
        telefone1.setId(1L);
        Telefone telefone2 = new Telefone();
        telefone2.setId(telefone1.getId());
        assertThat(telefone1).isEqualTo(telefone2);
        telefone2.setId(2L);
        assertThat(telefone1).isNotEqualTo(telefone2);
        telefone1.setId(null);
        assertThat(telefone1).isNotEqualTo(telefone2);
    }
}
