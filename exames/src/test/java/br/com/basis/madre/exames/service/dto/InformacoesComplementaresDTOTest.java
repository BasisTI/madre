package br.com.basis.madre.exames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class InformacoesComplementaresDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformacoesComplementaresDTO.class);
        InformacoesComplementaresDTO informacoesComplementaresDTO1 = new InformacoesComplementaresDTO();
        informacoesComplementaresDTO1.setId(1L);
        InformacoesComplementaresDTO informacoesComplementaresDTO2 = new InformacoesComplementaresDTO();
        assertThat(informacoesComplementaresDTO1).isNotEqualTo(informacoesComplementaresDTO2);
        informacoesComplementaresDTO2.setId(informacoesComplementaresDTO1.getId());
        assertThat(informacoesComplementaresDTO1).isEqualTo(informacoesComplementaresDTO2);
        informacoesComplementaresDTO2.setId(2L);
        assertThat(informacoesComplementaresDTO1).isNotEqualTo(informacoesComplementaresDTO2);
        informacoesComplementaresDTO1.setId(null);
        assertThat(informacoesComplementaresDTO1).isNotEqualTo(informacoesComplementaresDTO2);
    }
}
