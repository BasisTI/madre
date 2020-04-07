package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class NaturalidadeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NaturalidadeDTO.class);
        NaturalidadeDTO naturalidadeDTO1 = new NaturalidadeDTO();
        naturalidadeDTO1.setId(1L);
        NaturalidadeDTO naturalidadeDTO2 = new NaturalidadeDTO();
        assertThat(naturalidadeDTO1).isNotEqualTo(naturalidadeDTO2);
        naturalidadeDTO2.setId(naturalidadeDTO1.getId());
        assertThat(naturalidadeDTO1).isEqualTo(naturalidadeDTO2);
        naturalidadeDTO2.setId(2L);
        assertThat(naturalidadeDTO1).isNotEqualTo(naturalidadeDTO2);
        naturalidadeDTO1.setId(null);
        assertThat(naturalidadeDTO1).isNotEqualTo(naturalidadeDTO2);
    }
}
