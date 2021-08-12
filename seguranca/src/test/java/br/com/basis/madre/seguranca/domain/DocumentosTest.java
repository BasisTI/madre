package br.com.basis.madre.seguranca.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class DocumentosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Documentos.class);
        Documentos documentos1 = new Documentos();
        documentos1.setId(1L);
        Documentos documentos2 = new Documentos();
        documentos2.setId(documentos1.getId());
        assertThat(documentos1).isEqualTo(documentos2);
        documentos2.setId(2L);
        assertThat(documentos1).isNotEqualTo(documentos2);
        documentos1.setId(null);
        assertThat(documentos1).isNotEqualTo(documentos2);
    }
}
