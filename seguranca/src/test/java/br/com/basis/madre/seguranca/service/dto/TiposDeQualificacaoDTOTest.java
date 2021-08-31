package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class TiposDeQualificacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TiposDeQualificacaoDTO.class);
        TiposDeQualificacaoDTO tiposDeQualificacaoDTO1 = new TiposDeQualificacaoDTO();
        tiposDeQualificacaoDTO1.setId(1L);
        TiposDeQualificacaoDTO tiposDeQualificacaoDTO2 = new TiposDeQualificacaoDTO();
        assertThat(tiposDeQualificacaoDTO1).isNotEqualTo(tiposDeQualificacaoDTO2);
        tiposDeQualificacaoDTO2.setId(tiposDeQualificacaoDTO1.getId());
        assertThat(tiposDeQualificacaoDTO1).isEqualTo(tiposDeQualificacaoDTO2);
        tiposDeQualificacaoDTO2.setId(2L);
        assertThat(tiposDeQualificacaoDTO1).isNotEqualTo(tiposDeQualificacaoDTO2);
        tiposDeQualificacaoDTO1.setId(null);
        assertThat(tiposDeQualificacaoDTO1).isNotEqualTo(tiposDeQualificacaoDTO2);
    }
}
