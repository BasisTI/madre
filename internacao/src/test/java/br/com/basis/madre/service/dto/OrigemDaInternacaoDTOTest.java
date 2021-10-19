package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class OrigemDaInternacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigemDaInternacaoDTO.class);
        OrigemDaInternacaoDTO origemDaInternacaoDTO1 = new OrigemDaInternacaoDTO();
        origemDaInternacaoDTO1.setId(1L);
        OrigemDaInternacaoDTO origemDaInternacaoDTO2 = new OrigemDaInternacaoDTO();
        assertThat(origemDaInternacaoDTO1).isNotEqualTo(origemDaInternacaoDTO2);
        origemDaInternacaoDTO2.setId(origemDaInternacaoDTO1.getId());
        assertThat(origemDaInternacaoDTO1).isEqualTo(origemDaInternacaoDTO2);
        origemDaInternacaoDTO2.setId(2L);
        assertThat(origemDaInternacaoDTO1).isNotEqualTo(origemDaInternacaoDTO2);
        origemDaInternacaoDTO1.setId(null);
        assertThat(origemDaInternacaoDTO1).isNotEqualTo(origemDaInternacaoDTO2);
    }
}
