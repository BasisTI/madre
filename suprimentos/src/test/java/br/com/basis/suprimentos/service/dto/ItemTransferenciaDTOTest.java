package br.com.basis.suprimentos.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class ItemTransferenciaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemTransferenciaDTO.class);
        ItemTransferenciaDTO itemTransferenciaDTO1 = new ItemTransferenciaDTO();
        itemTransferenciaDTO1.setId(1L);
        ItemTransferenciaDTO itemTransferenciaDTO2 = new ItemTransferenciaDTO();
        assertThat(itemTransferenciaDTO1).isNotEqualTo(itemTransferenciaDTO2);
        itemTransferenciaDTO2.setId(itemTransferenciaDTO1.getId());
        assertThat(itemTransferenciaDTO1).isEqualTo(itemTransferenciaDTO2);
        itemTransferenciaDTO2.setId(2L);
        assertThat(itemTransferenciaDTO1).isNotEqualTo(itemTransferenciaDTO2);
        itemTransferenciaDTO1.setId(null);
        assertThat(itemTransferenciaDTO1).isNotEqualTo(itemTransferenciaDTO2);
    }
}
