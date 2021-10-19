package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class LocalDeAtendimentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalDeAtendimentoDTO.class);
        LocalDeAtendimentoDTO localDeAtendimentoDTO1 = new LocalDeAtendimentoDTO();
        localDeAtendimentoDTO1.setId(1L);
        LocalDeAtendimentoDTO localDeAtendimentoDTO2 = new LocalDeAtendimentoDTO();
        assertThat(localDeAtendimentoDTO1).isNotEqualTo(localDeAtendimentoDTO2);
        localDeAtendimentoDTO2.setId(localDeAtendimentoDTO1.getId());
        assertThat(localDeAtendimentoDTO1).isEqualTo(localDeAtendimentoDTO2);
        localDeAtendimentoDTO2.setId(2L);
        assertThat(localDeAtendimentoDTO1).isNotEqualTo(localDeAtendimentoDTO2);
        localDeAtendimentoDTO1.setId(null);
        assertThat(localDeAtendimentoDTO1).isNotEqualTo(localDeAtendimentoDTO2);
    }
}
