package br.com.basis.suprimentos.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class ItemTransferenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemTransferencia.class);
        ItemTransferencia itemTransferencia1 = new ItemTransferencia();
        itemTransferencia1.setId(1L);
        ItemTransferencia itemTransferencia2 = new ItemTransferencia();
        itemTransferencia2.setId(itemTransferencia1.getId());
        assertThat(itemTransferencia1).isEqualTo(itemTransferencia2);
        itemTransferencia2.setId(2L);
        assertThat(itemTransferencia1).isNotEqualTo(itemTransferencia2);
        itemTransferencia1.setId(null);
        assertThat(itemTransferencia1).isNotEqualTo(itemTransferencia2);
    }
}
