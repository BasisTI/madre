package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HospitalMapperTest {

    private HospitalMapper hospitalMapper;

    @BeforeEach
    public void setUp() {
        hospitalMapper = new HospitalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(hospitalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(hospitalMapper.fromId(null)).isNull();
    }
}
