package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class HospitalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hospital.class);
        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        Hospital hospital2 = new Hospital();
        hospital2.setId(hospital1.getId());
        assertThat(hospital1).isEqualTo(hospital2);
        hospital2.setId(2L);
        assertThat(hospital1).isNotEqualTo(hospital2);
        hospital1.setId(null);
        assertThat(hospital1).isNotEqualTo(hospital2);
    }
}
