package br.com.basis.madre.seguranca.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.seguranca.web.rest.TestUtil;

public class DocumentosDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentosDTO.class);
        DocumentosDTO documentosDTO1 = new DocumentosDTO();
        documentosDTO1.setId(1L);
        DocumentosDTO documentosDTO2 = new DocumentosDTO();
        assertThat(documentosDTO1).isNotEqualTo(documentosDTO2);
        documentosDTO2.setId(documentosDTO1.getId());
        assertThat(documentosDTO1).isEqualTo(documentosDTO2);
        documentosDTO2.setId(2L);
        assertThat(documentosDTO1).isNotEqualTo(documentosDTO2);
        documentosDTO1.setId(null);
        assertThat(documentosDTO1).isNotEqualTo(documentosDTO2);
    }
}
