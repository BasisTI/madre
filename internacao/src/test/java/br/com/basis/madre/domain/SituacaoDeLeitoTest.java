package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class SituacaoDeLeitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituacaoDeLeito.class);
        SituacaoDeLeito situacaoDeLeito1 = new SituacaoDeLeito();
        situacaoDeLeito1.setId(1L);
        SituacaoDeLeito situacaoDeLeito2 = new SituacaoDeLeito();
        situacaoDeLeito2.setId(situacaoDeLeito1.getId());
        assertThat(situacaoDeLeito1).isEqualTo(situacaoDeLeito2);
        situacaoDeLeito2.setId(2L);
        assertThat(situacaoDeLeito1).isNotEqualTo(situacaoDeLeito2);
        situacaoDeLeito1.setId(null);
        assertThat(situacaoDeLeito1).isNotEqualTo(situacaoDeLeito2);
    }
}
