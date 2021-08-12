package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class OcupacaoDeCargoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OcupacaoDeCargoDTO.class);
        OcupacaoDeCargoDTO ocupacaoDeCargoDTO1 = new OcupacaoDeCargoDTO();
        ocupacaoDeCargoDTO1.setId(1L);
        OcupacaoDeCargoDTO ocupacaoDeCargoDTO2 = new OcupacaoDeCargoDTO();
        assertThat(ocupacaoDeCargoDTO1).isNotEqualTo(ocupacaoDeCargoDTO2);
        ocupacaoDeCargoDTO2.setId(ocupacaoDeCargoDTO1.getId());
        assertThat(ocupacaoDeCargoDTO1).isEqualTo(ocupacaoDeCargoDTO2);
        ocupacaoDeCargoDTO2.setId(2L);
        assertThat(ocupacaoDeCargoDTO1).isNotEqualTo(ocupacaoDeCargoDTO2);
        ocupacaoDeCargoDTO1.setId(null);
        assertThat(ocupacaoDeCargoDTO1).isNotEqualTo(ocupacaoDeCargoDTO2);
    }
}
