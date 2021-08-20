package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class RamalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RamalDTO.class);
        RamalDTO ramalDTO1 = new RamalDTO();
        ramalDTO1.setId(1L);
        RamalDTO ramalDTO2 = new RamalDTO();
        assertThat(ramalDTO1).isNotEqualTo(ramalDTO2);
        ramalDTO2.setId(ramalDTO1.getId());
        assertThat(ramalDTO1).isEqualTo(ramalDTO2);
        ramalDTO2.setId(2L);
        assertThat(ramalDTO1).isNotEqualTo(ramalDTO2);
        ramalDTO1.setId(null);
        assertThat(ramalDTO1).isNotEqualTo(ramalDTO2);
    }
}
