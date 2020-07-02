package br.com.basis.suprimentos.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class ClassificacaoMaterialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassificacaoMaterial.class);
        ClassificacaoMaterial classificacaoMaterial1 = new ClassificacaoMaterial();
        classificacaoMaterial1.setId(1L);
        ClassificacaoMaterial classificacaoMaterial2 = new ClassificacaoMaterial();
        classificacaoMaterial2.setId(classificacaoMaterial1.getId());
        assertThat(classificacaoMaterial1).isEqualTo(classificacaoMaterial2);
        classificacaoMaterial2.setId(2L);
        assertThat(classificacaoMaterial1).isNotEqualTo(classificacaoMaterial2);
        classificacaoMaterial1.setId(null);
        assertThat(classificacaoMaterial1).isNotEqualTo(classificacaoMaterial2);
    }
}
