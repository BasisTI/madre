package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class CargoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CargoDTO.class);
        CargoDTO cargoDTO1 = new CargoDTO();
        cargoDTO1.setId(1L);
        CargoDTO cargoDTO2 = new CargoDTO();
        assertThat(cargoDTO1).isNotEqualTo(cargoDTO2);
        cargoDTO2.setId(cargoDTO1.getId());
        assertThat(cargoDTO1).isEqualTo(cargoDTO2);
        cargoDTO2.setId(2L);
        assertThat(cargoDTO1).isNotEqualTo(cargoDTO2);
        cargoDTO1.setId(null);
        assertThat(cargoDTO1).isNotEqualTo(cargoDTO2);
    }
}
