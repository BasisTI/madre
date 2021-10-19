package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ModalidadeAssistencialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModalidadeAssistencial.class);
        ModalidadeAssistencial modalidadeAssistencial1 = new ModalidadeAssistencial();
        modalidadeAssistencial1.setId(1L);
        ModalidadeAssistencial modalidadeAssistencial2 = new ModalidadeAssistencial();
        modalidadeAssistencial2.setId(modalidadeAssistencial1.getId());
        assertThat(modalidadeAssistencial1).isEqualTo(modalidadeAssistencial2);
        modalidadeAssistencial2.setId(2L);
        assertThat(modalidadeAssistencial1).isNotEqualTo(modalidadeAssistencial2);
        modalidadeAssistencial1.setId(null);
        assertThat(modalidadeAssistencial1).isNotEqualTo(modalidadeAssistencial2);
    }
}
