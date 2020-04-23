package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class MotivoDoCadastroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivoDoCadastro.class);
        MotivoDoCadastro motivoDoCadastro1 = new MotivoDoCadastro();
        motivoDoCadastro1.setId(1L);
        MotivoDoCadastro motivoDoCadastro2 = new MotivoDoCadastro();
        motivoDoCadastro2.setId(motivoDoCadastro1.getId());
        assertThat(motivoDoCadastro1).isEqualTo(motivoDoCadastro2);
        motivoDoCadastro2.setId(2L);
        assertThat(motivoDoCadastro1).isNotEqualTo(motivoDoCadastro2);
        motivoDoCadastro1.setId(null);
        assertThat(motivoDoCadastro1).isNotEqualTo(motivoDoCadastro2);
    }
}
