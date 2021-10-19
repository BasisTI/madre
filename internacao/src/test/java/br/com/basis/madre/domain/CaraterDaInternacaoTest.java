package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class CaraterDaInternacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaraterDaInternacao.class);
        CaraterDaInternacao caraterDaInternacao1 = new CaraterDaInternacao();
        caraterDaInternacao1.setId(1L);
        CaraterDaInternacao caraterDaInternacao2 = new CaraterDaInternacao();
        caraterDaInternacao2.setId(caraterDaInternacao1.getId());
        assertThat(caraterDaInternacao1).isEqualTo(caraterDaInternacao2);
        caraterDaInternacao2.setId(2L);
        assertThat(caraterDaInternacao1).isNotEqualTo(caraterDaInternacao2);
        caraterDaInternacao1.setId(null);
        assertThat(caraterDaInternacao1).isNotEqualTo(caraterDaInternacao2);
    }
}
