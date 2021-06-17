package br.com.basis.madre.exames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemSolicitacaoExameMapperTest {

    private ItemSolicitacaoExameMapper itemSolicitacaoExameMapper;

    @BeforeEach
    public void setUp() {
        itemSolicitacaoExameMapper = new ItemSolicitacaoExameMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(itemSolicitacaoExameMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(itemSolicitacaoExameMapper.fromId(null)).isNull();
    }
}
