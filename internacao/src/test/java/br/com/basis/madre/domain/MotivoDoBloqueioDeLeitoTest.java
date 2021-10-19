package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class MotivoDoBloqueioDeLeitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivoDoBloqueioDeLeito.class);
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito1 = new MotivoDoBloqueioDeLeito();
        motivoDoBloqueioDeLeito1.setId(1L);
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito2 = new MotivoDoBloqueioDeLeito();
        motivoDoBloqueioDeLeito2.setId(motivoDoBloqueioDeLeito1.getId());
        assertThat(motivoDoBloqueioDeLeito1).isEqualTo(motivoDoBloqueioDeLeito2);
        motivoDoBloqueioDeLeito2.setId(2L);
        assertThat(motivoDoBloqueioDeLeito1).isNotEqualTo(motivoDoBloqueioDeLeito2);
        motivoDoBloqueioDeLeito1.setId(null);
        assertThat(motivoDoBloqueioDeLeito1).isNotEqualTo(motivoDoBloqueioDeLeito2);
    }
}
