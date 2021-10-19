package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class OrigemDaInternacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigemDaInternacao.class);
        OrigemDaInternacao origemDaInternacao1 = new OrigemDaInternacao();
        origemDaInternacao1.setId(1L);
        OrigemDaInternacao origemDaInternacao2 = new OrigemDaInternacao();
        origemDaInternacao2.setId(origemDaInternacao1.getId());
        assertThat(origemDaInternacao1).isEqualTo(origemDaInternacao2);
        origemDaInternacao2.setId(2L);
        assertThat(origemDaInternacao1).isNotEqualTo(origemDaInternacao2);
        origemDaInternacao1.setId(null);
        assertThat(origemDaInternacao1).isNotEqualTo(origemDaInternacao2);
    }
}
