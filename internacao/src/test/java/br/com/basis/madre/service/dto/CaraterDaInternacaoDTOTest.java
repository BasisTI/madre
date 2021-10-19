package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class CaraterDaInternacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaraterDaInternacaoDTO.class);
        CaraterDaInternacaoDTO caraterDaInternacaoDTO1 = new CaraterDaInternacaoDTO();
        caraterDaInternacaoDTO1.setId(1L);
        CaraterDaInternacaoDTO caraterDaInternacaoDTO2 = new CaraterDaInternacaoDTO();
        assertThat(caraterDaInternacaoDTO1).isNotEqualTo(caraterDaInternacaoDTO2);
        caraterDaInternacaoDTO2.setId(caraterDaInternacaoDTO1.getId());
        assertThat(caraterDaInternacaoDTO1).isEqualTo(caraterDaInternacaoDTO2);
        caraterDaInternacaoDTO2.setId(2L);
        assertThat(caraterDaInternacaoDTO1).isNotEqualTo(caraterDaInternacaoDTO2);
        caraterDaInternacaoDTO1.setId(null);
        assertThat(caraterDaInternacaoDTO1).isNotEqualTo(caraterDaInternacaoDTO2);
    }
}
