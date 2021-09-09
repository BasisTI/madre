package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class MaterialDeExameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialDeExame.class);
        MaterialDeExame materialDeExame1 = new MaterialDeExame();
        materialDeExame1.setId(1L);
        MaterialDeExame materialDeExame2 = new MaterialDeExame();
        materialDeExame2.setId(materialDeExame1.getId());
        assertThat(materialDeExame1).isEqualTo(materialDeExame2);
        materialDeExame2.setId(2L);
        assertThat(materialDeExame1).isNotEqualTo(materialDeExame2);
        materialDeExame1.setId(null);
        assertThat(materialDeExame1).isNotEqualTo(materialDeExame2);
    }
}
