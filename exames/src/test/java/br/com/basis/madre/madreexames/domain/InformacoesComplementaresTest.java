package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class InformacoesComplementaresTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformacoesComplementares.class);
        InformacoesComplementares informacoesComplementares1 = new InformacoesComplementares();
        informacoesComplementares1.setId(1L);
        InformacoesComplementares informacoesComplementares2 = new InformacoesComplementares();
        informacoesComplementares2.setId(informacoesComplementares1.getId());
        assertThat(informacoesComplementares1).isEqualTo(informacoesComplementares2);
        informacoesComplementares2.setId(2L);
        assertThat(informacoesComplementares1).isNotEqualTo(informacoesComplementares2);
        informacoesComplementares1.setId(null);
        assertThat(informacoesComplementares1).isNotEqualTo(informacoesComplementares2);
    }
}
