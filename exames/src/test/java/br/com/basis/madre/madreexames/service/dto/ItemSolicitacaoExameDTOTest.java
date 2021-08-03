package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class ItemSolicitacaoExameDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemSolicitacaoExameDTO.class);
        ItemSolicitacaoExameDTO itemSolicitacaoExameDTO1 = new ItemSolicitacaoExameDTO();
        itemSolicitacaoExameDTO1.setId(1L);
        ItemSolicitacaoExameDTO itemSolicitacaoExameDTO2 = new ItemSolicitacaoExameDTO();
        assertThat(itemSolicitacaoExameDTO1).isNotEqualTo(itemSolicitacaoExameDTO2);
        itemSolicitacaoExameDTO2.setId(itemSolicitacaoExameDTO1.getId());
        assertThat(itemSolicitacaoExameDTO1).isEqualTo(itemSolicitacaoExameDTO2);
        itemSolicitacaoExameDTO2.setId(2L);
        assertThat(itemSolicitacaoExameDTO1).isNotEqualTo(itemSolicitacaoExameDTO2);
        itemSolicitacaoExameDTO1.setId(null);
        assertThat(itemSolicitacaoExameDTO1).isNotEqualTo(itemSolicitacaoExameDTO2);
    }
}
