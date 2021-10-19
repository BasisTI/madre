package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class EspecialidadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Especialidade.class);
        Especialidade especialidade1 = new Especialidade();
        especialidade1.setId(1L);
        Especialidade especialidade2 = new Especialidade();
        especialidade2.setId(especialidade1.getId());
        assertThat(especialidade1).isEqualTo(especialidade2);
        especialidade2.setId(2L);
        assertThat(especialidade1).isNotEqualTo(especialidade2);
        especialidade1.setId(null);
        assertThat(especialidade1).isNotEqualTo(especialidade2);
    }
}
