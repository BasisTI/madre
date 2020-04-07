package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DocumentoMapperTest {

    private DocumentoMapper documentoMapper;

    @BeforeEach
    public void setUp() {
        documentoMapper = new DocumentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(documentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(documentoMapper.fromId(null)).isNull();
    }
}
