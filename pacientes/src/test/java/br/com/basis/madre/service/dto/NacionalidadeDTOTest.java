package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class NacionalidadeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NacionalidadeDTO.class);
        NacionalidadeDTO nacionalidadeDTO1 = new NacionalidadeDTO();
        nacionalidadeDTO1.setId(1L);
        NacionalidadeDTO nacionalidadeDTO2 = new NacionalidadeDTO();
        assertThat(nacionalidadeDTO1).isNotEqualTo(nacionalidadeDTO2);
        nacionalidadeDTO2.setId(nacionalidadeDTO1.getId());
        assertThat(nacionalidadeDTO1).isEqualTo(nacionalidadeDTO2);
        nacionalidadeDTO2.setId(2L);
        assertThat(nacionalidadeDTO1).isNotEqualTo(nacionalidadeDTO2);
        nacionalidadeDTO1.setId(null);
        assertThat(nacionalidadeDTO1).isNotEqualTo(nacionalidadeDTO2);
    }
}
