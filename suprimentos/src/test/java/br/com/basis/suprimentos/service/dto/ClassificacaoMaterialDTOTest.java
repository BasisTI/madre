package br.com.basis.suprimentos.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class ClassificacaoMaterialDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassificacaoMaterialDTO.class);
        ClassificacaoMaterialDTO classificacaoMaterialDTO1 = new ClassificacaoMaterialDTO();
        classificacaoMaterialDTO1.setId(1L);
        ClassificacaoMaterialDTO classificacaoMaterialDTO2 = new ClassificacaoMaterialDTO();
        assertThat(classificacaoMaterialDTO1).isNotEqualTo(classificacaoMaterialDTO2);
        classificacaoMaterialDTO2.setId(classificacaoMaterialDTO1.getId());
        assertThat(classificacaoMaterialDTO1).isEqualTo(classificacaoMaterialDTO2);
        classificacaoMaterialDTO2.setId(2L);
        assertThat(classificacaoMaterialDTO1).isNotEqualTo(classificacaoMaterialDTO2);
        classificacaoMaterialDTO1.setId(null);
        assertThat(classificacaoMaterialDTO1).isNotEqualTo(classificacaoMaterialDTO2);
    }
}
