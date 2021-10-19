package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CIDMapperTest {

    private CIDMapper cIDMapper;

    @BeforeEach
    public void setUp() {
        cIDMapper = new CIDMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cIDMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cIDMapper.fromId(null)).isNull();
    }
}
