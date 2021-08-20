package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CargoMapperTest {

    private CargoMapper cargoMapper;

    @BeforeEach
    public void setUp() {
        cargoMapper = new CargoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cargoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cargoMapper.fromId(null)).isNull();
    }
}
