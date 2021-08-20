package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class TiposDeQualificacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TiposDeQualificacao.class);
        TiposDeQualificacao tiposDeQualificacao1 = new TiposDeQualificacao();
        tiposDeQualificacao1.setId(1L);
        TiposDeQualificacao tiposDeQualificacao2 = new TiposDeQualificacao();
        tiposDeQualificacao2.setId(tiposDeQualificacao1.getId());
        assertThat(tiposDeQualificacao1).isEqualTo(tiposDeQualificacao2);
        tiposDeQualificacao2.setId(2L);
        assertThat(tiposDeQualificacao1).isNotEqualTo(tiposDeQualificacao2);
        tiposDeQualificacao1.setId(null);
        assertThat(tiposDeQualificacao1).isNotEqualTo(tiposDeQualificacao2);
    }
}
