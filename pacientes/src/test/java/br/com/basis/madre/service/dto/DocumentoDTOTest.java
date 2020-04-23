package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class DocumentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentoDTO.class);
        DocumentoDTO documentoDTO1 = new DocumentoDTO();
        documentoDTO1.setId(1L);
        DocumentoDTO documentoDTO2 = new DocumentoDTO();
        assertThat(documentoDTO1).isNotEqualTo(documentoDTO2);
        documentoDTO2.setId(documentoDTO1.getId());
        assertThat(documentoDTO1).isEqualTo(documentoDTO2);
        documentoDTO2.setId(2L);
        assertThat(documentoDTO1).isNotEqualTo(documentoDTO2);
        documentoDTO1.setId(null);
        assertThat(documentoDTO1).isNotEqualTo(documentoDTO2);
    }
}
