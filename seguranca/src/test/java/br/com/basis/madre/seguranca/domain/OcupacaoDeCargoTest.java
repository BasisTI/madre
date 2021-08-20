package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class OcupacaoDeCargoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OcupacaoDeCargo.class);
        OcupacaoDeCargo ocupacaoDeCargo1 = new OcupacaoDeCargo();
        ocupacaoDeCargo1.setId(1L);
        OcupacaoDeCargo ocupacaoDeCargo2 = new OcupacaoDeCargo();
        ocupacaoDeCargo2.setId(ocupacaoDeCargo1.getId());
        assertThat(ocupacaoDeCargo1).isEqualTo(ocupacaoDeCargo2);
        ocupacaoDeCargo2.setId(2L);
        assertThat(ocupacaoDeCargo1).isNotEqualTo(ocupacaoDeCargo2);
        ocupacaoDeCargo1.setId(null);
        assertThat(ocupacaoDeCargo1).isNotEqualTo(ocupacaoDeCargo2);
    }
}
