package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class ConselhosProfissionaisDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConselhosProfissionaisDTO.class);
        ConselhosProfissionaisDTO conselhosProfissionaisDTO1 = new ConselhosProfissionaisDTO();
        conselhosProfissionaisDTO1.setId(1L);
        ConselhosProfissionaisDTO conselhosProfissionaisDTO2 = new ConselhosProfissionaisDTO();
        assertThat(conselhosProfissionaisDTO1).isNotEqualTo(conselhosProfissionaisDTO2);
        conselhosProfissionaisDTO2.setId(conselhosProfissionaisDTO1.getId());
        assertThat(conselhosProfissionaisDTO1).isEqualTo(conselhosProfissionaisDTO2);
        conselhosProfissionaisDTO2.setId(2L);
        assertThat(conselhosProfissionaisDTO1).isNotEqualTo(conselhosProfissionaisDTO2);
        conselhosProfissionaisDTO1.setId(null);
        assertThat(conselhosProfissionaisDTO1).isNotEqualTo(conselhosProfissionaisDTO2);
    }
}
