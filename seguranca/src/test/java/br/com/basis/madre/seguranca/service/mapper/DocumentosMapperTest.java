package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DocumentosMapperTest {

    private DocumentosMapper documentosMapper;

    @BeforeEach
    public void setUp() {
        documentosMapper = new DocumentosMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(documentosMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(documentosMapper.fromId(null)).isNull();
    }
}
