package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class ControleQualidadeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControleQualidadeDTO.class);
        ControleQualidadeDTO controleQualidadeDTO1 = new ControleQualidadeDTO();
        controleQualidadeDTO1.setId(1L);
        ControleQualidadeDTO controleQualidadeDTO2 = new ControleQualidadeDTO();
        assertThat(controleQualidadeDTO1).isNotEqualTo(controleQualidadeDTO2);
        controleQualidadeDTO2.setId(controleQualidadeDTO1.getId());
        assertThat(controleQualidadeDTO1).isEqualTo(controleQualidadeDTO2);
        controleQualidadeDTO2.setId(2L);
        assertThat(controleQualidadeDTO1).isNotEqualTo(controleQualidadeDTO2);
        controleQualidadeDTO1.setId(null);
        assertThat(controleQualidadeDTO1).isNotEqualTo(controleQualidadeDTO2);
    }
}
