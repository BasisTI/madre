package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class LocalDeAtendimentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalDeAtendimento.class);
        LocalDeAtendimento localDeAtendimento1 = new LocalDeAtendimento();
        localDeAtendimento1.setId(1L);
        LocalDeAtendimento localDeAtendimento2 = new LocalDeAtendimento();
        localDeAtendimento2.setId(localDeAtendimento1.getId());
        assertThat(localDeAtendimento1).isEqualTo(localDeAtendimento2);
        localDeAtendimento2.setId(2L);
        assertThat(localDeAtendimento1).isNotEqualTo(localDeAtendimento2);
        localDeAtendimento1.setId(null);
        assertThat(localDeAtendimento1).isNotEqualTo(localDeAtendimento2);
    }
}
