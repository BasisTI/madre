package br.com.basis.suprimentos.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.suprimentos.web.rest.TestUtil;

public class EstoqueGeralDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstoqueGeralDTO.class);
        EstoqueGeralDTO estoqueGeralDTO1 = new EstoqueGeralDTO();
        estoqueGeralDTO1.setId(1L);
        EstoqueGeralDTO estoqueGeralDTO2 = new EstoqueGeralDTO();
        assertThat(estoqueGeralDTO1).isNotEqualTo(estoqueGeralDTO2);
        estoqueGeralDTO2.setId(estoqueGeralDTO1.getId());
        assertThat(estoqueGeralDTO1).isEqualTo(estoqueGeralDTO2);
        estoqueGeralDTO2.setId(2L);
        assertThat(estoqueGeralDTO1).isNotEqualTo(estoqueGeralDTO2);
        estoqueGeralDTO1.setId(null);
        assertThat(estoqueGeralDTO1).isNotEqualTo(estoqueGeralDTO2);
    }
}
