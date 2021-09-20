package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class TipoDeMarcacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeMarcacao.class);
        TipoDeMarcacao tipoDeMarcacao1 = new TipoDeMarcacao();
        tipoDeMarcacao1.setId(1L);
        TipoDeMarcacao tipoDeMarcacao2 = new TipoDeMarcacao();
        tipoDeMarcacao2.setId(tipoDeMarcacao1.getId());
        assertThat(tipoDeMarcacao1).isEqualTo(tipoDeMarcacao2);
        tipoDeMarcacao2.setId(2L);
        assertThat(tipoDeMarcacao1).isNotEqualTo(tipoDeMarcacao2);
        tipoDeMarcacao1.setId(null);
        assertThat(tipoDeMarcacao1).isNotEqualTo(tipoDeMarcacao2);
    }
}
