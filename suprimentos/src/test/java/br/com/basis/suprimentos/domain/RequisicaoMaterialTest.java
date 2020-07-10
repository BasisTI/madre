package br.com.basis.suprimentos.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class RequisicaoMaterialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequisicaoMaterial.class);
        RequisicaoMaterial requisicaoMaterial1 = new RequisicaoMaterial();
        requisicaoMaterial1.setId(1L);
        RequisicaoMaterial requisicaoMaterial2 = new RequisicaoMaterial();
        requisicaoMaterial2.setId(requisicaoMaterial1.getId());
        assertThat(requisicaoMaterial1).isEqualTo(requisicaoMaterial2);
        requisicaoMaterial2.setId(2L);
        assertThat(requisicaoMaterial1).isNotEqualTo(requisicaoMaterial2);
        requisicaoMaterial1.setId(null);
        assertThat(requisicaoMaterial1).isNotEqualTo(requisicaoMaterial2);
    }
}
