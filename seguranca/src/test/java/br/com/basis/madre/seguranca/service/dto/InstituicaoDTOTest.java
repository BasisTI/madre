package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class InstituicaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstituicaoDTO.class);
        InstituicaoDTO instituicaoDTO1 = new InstituicaoDTO();
        instituicaoDTO1.setId(1L);
        InstituicaoDTO instituicaoDTO2 = new InstituicaoDTO();
        assertThat(instituicaoDTO1).isNotEqualTo(instituicaoDTO2);
        instituicaoDTO2.setId(instituicaoDTO1.getId());
        assertThat(instituicaoDTO1).isEqualTo(instituicaoDTO2);
        instituicaoDTO2.setId(2L);
        assertThat(instituicaoDTO1).isNotEqualTo(instituicaoDTO2);
        instituicaoDTO1.setId(null);
        assertThat(instituicaoDTO1).isNotEqualTo(instituicaoDTO2);
    }
}
