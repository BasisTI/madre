package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class MotivoDoCadastroDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivoDoCadastroDTO.class);
        MotivoDoCadastroDTO motivoDoCadastroDTO1 = new MotivoDoCadastroDTO();
        motivoDoCadastroDTO1.setId(1L);
        MotivoDoCadastroDTO motivoDoCadastroDTO2 = new MotivoDoCadastroDTO();
        assertThat(motivoDoCadastroDTO1).isNotEqualTo(motivoDoCadastroDTO2);
        motivoDoCadastroDTO2.setId(motivoDoCadastroDTO1.getId());
        assertThat(motivoDoCadastroDTO1).isEqualTo(motivoDoCadastroDTO2);
        motivoDoCadastroDTO2.setId(2L);
        assertThat(motivoDoCadastroDTO1).isNotEqualTo(motivoDoCadastroDTO2);
        motivoDoCadastroDTO1.setId(null);
        assertThat(motivoDoCadastroDTO1).isNotEqualTo(motivoDoCadastroDTO2);
    }
}
