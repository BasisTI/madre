package br.com.basis.suprimentos.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class RequisicaoMaterialDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequisicaoMaterialDTO.class);
        RequisicaoMaterialDTO requisicaoMaterialDTO1 = new RequisicaoMaterialDTO();
        requisicaoMaterialDTO1.setId(1L);
        RequisicaoMaterialDTO requisicaoMaterialDTO2 = new RequisicaoMaterialDTO();
        assertThat(requisicaoMaterialDTO1).isNotEqualTo(requisicaoMaterialDTO2);
        requisicaoMaterialDTO2.setId(requisicaoMaterialDTO1.getId());
        assertThat(requisicaoMaterialDTO1).isEqualTo(requisicaoMaterialDTO2);
        requisicaoMaterialDTO2.setId(2L);
        assertThat(requisicaoMaterialDTO1).isNotEqualTo(requisicaoMaterialDTO2);
        requisicaoMaterialDTO1.setId(null);
        assertThat(requisicaoMaterialDTO1).isNotEqualTo(requisicaoMaterialDTO2);
    }
}
