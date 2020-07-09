package br.com.basis.suprimentos.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemRequisicaoMapperTest {

    private ItemRequisicaoMapper itemRequisicaoMapper;

    @BeforeEach
    public void setUp() {
        itemRequisicaoMapper = new ItemRequisicaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(itemRequisicaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(itemRequisicaoMapper.fromId(null)).isNull();
    }
}
