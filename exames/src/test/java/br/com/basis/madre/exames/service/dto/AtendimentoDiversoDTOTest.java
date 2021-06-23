package br.com.basis.madre.exames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class AtendimentoDiversoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtendimentoDiversoDTO.class);
        AtendimentoDiversoDTO atendimentoDiversoDTO1 = new AtendimentoDiversoDTO();
        atendimentoDiversoDTO1.setId(1L);
        AtendimentoDiversoDTO atendimentoDiversoDTO2 = new AtendimentoDiversoDTO();
        assertThat(atendimentoDiversoDTO1).isNotEqualTo(atendimentoDiversoDTO2);
        atendimentoDiversoDTO2.setId(atendimentoDiversoDTO1.getId());
        assertThat(atendimentoDiversoDTO1).isEqualTo(atendimentoDiversoDTO2);
        atendimentoDiversoDTO2.setId(2L);
        assertThat(atendimentoDiversoDTO1).isNotEqualTo(atendimentoDiversoDTO2);
        atendimentoDiversoDTO1.setId(null);
        assertThat(atendimentoDiversoDTO1).isNotEqualTo(atendimentoDiversoDTO2);
    }
}
