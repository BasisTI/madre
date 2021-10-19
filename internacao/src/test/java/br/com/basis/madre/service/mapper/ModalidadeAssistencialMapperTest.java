package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ModalidadeAssistencialMapperTest {

    private ModalidadeAssistencialMapper modalidadeAssistencialMapper;

    @BeforeEach
    public void setUp() {
        modalidadeAssistencialMapper = new ModalidadeAssistencialMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(modalidadeAssistencialMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(modalidadeAssistencialMapper.fromId(null)).isNull();
    }
}
