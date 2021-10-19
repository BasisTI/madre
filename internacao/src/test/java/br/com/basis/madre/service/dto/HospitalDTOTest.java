package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class HospitalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HospitalDTO.class);
        HospitalDTO hospitalDTO1 = new HospitalDTO();
        hospitalDTO1.setId(1L);
        HospitalDTO hospitalDTO2 = new HospitalDTO();
        assertThat(hospitalDTO1).isNotEqualTo(hospitalDTO2);
        hospitalDTO2.setId(hospitalDTO1.getId());
        assertThat(hospitalDTO1).isEqualTo(hospitalDTO2);
        hospitalDTO2.setId(2L);
        assertThat(hospitalDTO1).isNotEqualTo(hospitalDTO2);
        hospitalDTO1.setId(null);
        assertThat(hospitalDTO1).isNotEqualTo(hospitalDTO2);
    }
}
