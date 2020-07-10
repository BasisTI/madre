package br.com.basis.suprimentos.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RequisicaoMaterialMapperTest {

    private RequisicaoMaterialMapper requisicaoMaterialMapper;

    @BeforeEach
    public void setUp() {
        requisicaoMaterialMapper = new RequisicaoMaterialMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(requisicaoMaterialMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(requisicaoMaterialMapper.fromId(null)).isNull();
    }
}
