package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ModalidadeAssistencialDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModalidadeAssistencialDTO.class);
        ModalidadeAssistencialDTO modalidadeAssistencialDTO1 = new ModalidadeAssistencialDTO();
        modalidadeAssistencialDTO1.setId(1L);
        ModalidadeAssistencialDTO modalidadeAssistencialDTO2 = new ModalidadeAssistencialDTO();
        assertThat(modalidadeAssistencialDTO1).isNotEqualTo(modalidadeAssistencialDTO2);
        modalidadeAssistencialDTO2.setId(modalidadeAssistencialDTO1.getId());
        assertThat(modalidadeAssistencialDTO1).isEqualTo(modalidadeAssistencialDTO2);
        modalidadeAssistencialDTO2.setId(2L);
        assertThat(modalidadeAssistencialDTO1).isNotEqualTo(modalidadeAssistencialDTO2);
        modalidadeAssistencialDTO1.setId(null);
        assertThat(modalidadeAssistencialDTO1).isNotEqualTo(modalidadeAssistencialDTO2);
    }
}
