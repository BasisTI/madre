package br.com.basis.madre.exames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.exames.web.rest.TestUtil;

public class ProjetoDePesquisaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjetoDePesquisa.class);
        ProjetoDePesquisa projetoDePesquisa1 = new ProjetoDePesquisa();
        projetoDePesquisa1.setId(1L);
        ProjetoDePesquisa projetoDePesquisa2 = new ProjetoDePesquisa();
        projetoDePesquisa2.setId(projetoDePesquisa1.getId());
        assertThat(projetoDePesquisa1).isEqualTo(projetoDePesquisa2);
        projetoDePesquisa2.setId(2L);
        assertThat(projetoDePesquisa1).isNotEqualTo(projetoDePesquisa2);
        projetoDePesquisa1.setId(null);
        assertThat(projetoDePesquisa1).isNotEqualTo(projetoDePesquisa2);
    }
}
