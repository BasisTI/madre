package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class TipoDoEventoLeitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDoEventoLeito.class);
        TipoDoEventoLeito tipoDoEventoLeito1 = new TipoDoEventoLeito();
        tipoDoEventoLeito1.setId(1L);
        TipoDoEventoLeito tipoDoEventoLeito2 = new TipoDoEventoLeito();
        tipoDoEventoLeito2.setId(tipoDoEventoLeito1.getId());
        assertThat(tipoDoEventoLeito1).isEqualTo(tipoDoEventoLeito2);
        tipoDoEventoLeito2.setId(2L);
        assertThat(tipoDoEventoLeito1).isNotEqualTo(tipoDoEventoLeito2);
        tipoDoEventoLeito1.setId(null);
        assertThat(tipoDoEventoLeito1).isNotEqualTo(tipoDoEventoLeito2);
    }
}
