package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class TipoDeMarcacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeMarcacaoDTO.class);
        TipoDeMarcacaoDTO tipoDeMarcacaoDTO1 = new TipoDeMarcacaoDTO();
        tipoDeMarcacaoDTO1.setId(1L);
        TipoDeMarcacaoDTO tipoDeMarcacaoDTO2 = new TipoDeMarcacaoDTO();
        assertThat(tipoDeMarcacaoDTO1).isNotEqualTo(tipoDeMarcacaoDTO2);
        tipoDeMarcacaoDTO2.setId(tipoDeMarcacaoDTO1.getId());
        assertThat(tipoDeMarcacaoDTO1).isEqualTo(tipoDeMarcacaoDTO2);
        tipoDeMarcacaoDTO2.setId(2L);
        assertThat(tipoDeMarcacaoDTO1).isNotEqualTo(tipoDeMarcacaoDTO2);
        tipoDeMarcacaoDTO1.setId(null);
        assertThat(tipoDeMarcacaoDTO1).isNotEqualTo(tipoDeMarcacaoDTO2);
    }
}
