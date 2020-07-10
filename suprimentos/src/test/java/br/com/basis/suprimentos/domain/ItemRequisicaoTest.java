package br.com.basis.suprimentos.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class ItemRequisicaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemRequisicao.class);
        ItemRequisicao itemRequisicao1 = new ItemRequisicao();
        itemRequisicao1.setId(1L);
        ItemRequisicao itemRequisicao2 = new ItemRequisicao();
        itemRequisicao2.setId(itemRequisicao1.getId());
        assertThat(itemRequisicao1).isEqualTo(itemRequisicao2);
        itemRequisicao2.setId(2L);
        assertThat(itemRequisicao1).isNotEqualTo(itemRequisicao2);
        itemRequisicao1.setId(null);
        assertThat(itemRequisicao1).isNotEqualTo(itemRequisicao2);
    }
}
