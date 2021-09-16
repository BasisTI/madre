package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class AnticoagulanteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anticoagulante.class);
        Anticoagulante anticoagulante1 = new Anticoagulante();
        anticoagulante1.setId(1L);
        Anticoagulante anticoagulante2 = new Anticoagulante();
        anticoagulante2.setId(anticoagulante1.getId());
        assertThat(anticoagulante1).isEqualTo(anticoagulante2);
        anticoagulante2.setId(2L);
        assertThat(anticoagulante1).isNotEqualTo(anticoagulante2);
        anticoagulante1.setId(null);
        assertThat(anticoagulante1).isNotEqualTo(anticoagulante2);
    }
}
