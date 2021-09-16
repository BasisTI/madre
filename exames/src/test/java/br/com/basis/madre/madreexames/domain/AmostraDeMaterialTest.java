package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class AmostraDeMaterialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmostraDeMaterial.class);
        AmostraDeMaterial amostraDeMaterial1 = new AmostraDeMaterial();
        amostraDeMaterial1.setId(1L);
        AmostraDeMaterial amostraDeMaterial2 = new AmostraDeMaterial();
        amostraDeMaterial2.setId(amostraDeMaterial1.getId());
        assertThat(amostraDeMaterial1).isEqualTo(amostraDeMaterial2);
        amostraDeMaterial2.setId(2L);
        assertThat(amostraDeMaterial1).isNotEqualTo(amostraDeMaterial2);
        amostraDeMaterial1.setId(null);
        assertThat(amostraDeMaterial1).isNotEqualTo(amostraDeMaterial2);
    }
}
