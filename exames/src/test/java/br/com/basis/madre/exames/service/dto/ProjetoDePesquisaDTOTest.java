package br.com.basis.madre.exames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class ProjetoDePesquisaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjetoDePesquisaDTO.class);
        ProjetoDePesquisaDTO projetoDePesquisaDTO1 = new ProjetoDePesquisaDTO();
        projetoDePesquisaDTO1.setId(1L);
        ProjetoDePesquisaDTO projetoDePesquisaDTO2 = new ProjetoDePesquisaDTO();
        assertThat(projetoDePesquisaDTO1).isNotEqualTo(projetoDePesquisaDTO2);
        projetoDePesquisaDTO2.setId(projetoDePesquisaDTO1.getId());
        assertThat(projetoDePesquisaDTO1).isEqualTo(projetoDePesquisaDTO2);
        projetoDePesquisaDTO2.setId(2L);
        assertThat(projetoDePesquisaDTO1).isNotEqualTo(projetoDePesquisaDTO2);
        projetoDePesquisaDTO1.setId(null);
        assertThat(projetoDePesquisaDTO1).isNotEqualTo(projetoDePesquisaDTO2);
    }
}
