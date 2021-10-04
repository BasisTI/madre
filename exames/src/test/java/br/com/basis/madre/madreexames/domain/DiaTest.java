package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class DiaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dia.class);
        Dia dia1 = new Dia();
        dia1.setId(1L);
        Dia dia2 = new Dia();
        dia2.setId(dia1.getId());
        assertThat(dia1).isEqualTo(dia2);
        dia2.setId(2L);
        assertThat(dia1).isNotEqualTo(dia2);
        dia1.setId(null);
        assertThat(dia1).isNotEqualTo(dia2);
    }
}
