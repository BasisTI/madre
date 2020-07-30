package br.com.basis.suprimentos.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class ItemRequisicaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemRequisicaoDTO.class);
        ItemRequisicaoDTO itemRequisicaoDTO1 = new ItemRequisicaoDTO();
        itemRequisicaoDTO1.setId(1L);
        ItemRequisicaoDTO itemRequisicaoDTO2 = new ItemRequisicaoDTO();
        assertThat(itemRequisicaoDTO1).isNotEqualTo(itemRequisicaoDTO2);
        itemRequisicaoDTO2.setId(itemRequisicaoDTO1.getId());
        assertThat(itemRequisicaoDTO1).isEqualTo(itemRequisicaoDTO2);
        itemRequisicaoDTO2.setId(2L);
        assertThat(itemRequisicaoDTO1).isNotEqualTo(itemRequisicaoDTO2);
        itemRequisicaoDTO1.setId(null);
        assertThat(itemRequisicaoDTO1).isNotEqualTo(itemRequisicaoDTO2);
    }
}
