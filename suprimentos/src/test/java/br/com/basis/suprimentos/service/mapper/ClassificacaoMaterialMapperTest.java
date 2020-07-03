package br.com.basis.suprimentos.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassificacaoMaterialMapperTest {

    private ClassificacaoMaterialMapper classificacaoMaterialMapper;

    @BeforeEach
    public void setUp() {
        classificacaoMaterialMapper = new ClassificacaoMaterialMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(classificacaoMaterialMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(classificacaoMaterialMapper.fromId(null)).isNull();
    }
}
