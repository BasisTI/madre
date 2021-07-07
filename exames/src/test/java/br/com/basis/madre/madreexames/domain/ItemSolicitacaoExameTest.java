package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class ItemSolicitacaoExameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemSolicitacaoExame.class);
        ItemSolicitacaoExame itemSolicitacaoExame1 = new ItemSolicitacaoExame();
        itemSolicitacaoExame1.setId(1L);
        ItemSolicitacaoExame itemSolicitacaoExame2 = new ItemSolicitacaoExame();
        itemSolicitacaoExame2.setId(itemSolicitacaoExame1.getId());
        assertThat(itemSolicitacaoExame1).isEqualTo(itemSolicitacaoExame2);
        itemSolicitacaoExame2.setId(2L);
        assertThat(itemSolicitacaoExame1).isNotEqualTo(itemSolicitacaoExame2);
        itemSolicitacaoExame1.setId(null);
        assertThat(itemSolicitacaoExame1).isNotEqualTo(itemSolicitacaoExame2);
    }
}
