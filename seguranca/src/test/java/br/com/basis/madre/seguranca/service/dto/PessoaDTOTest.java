package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class PessoaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PessoaDTO.class);
        PessoaDTO pessoaDTO1 = new PessoaDTO();
        pessoaDTO1.setId(1L);
        PessoaDTO pessoaDTO2 = new PessoaDTO();
        assertThat(pessoaDTO1).isNotEqualTo(pessoaDTO2);
        pessoaDTO2.setId(pessoaDTO1.getId());
        assertThat(pessoaDTO1).isEqualTo(pessoaDTO2);
        pessoaDTO2.setId(2L);
        assertThat(pessoaDTO1).isNotEqualTo(pessoaDTO2);
        pessoaDTO1.setId(null);
        assertThat(pessoaDTO1).isNotEqualTo(pessoaDTO2);
    }
}
