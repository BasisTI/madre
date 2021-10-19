package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CaraterDaInternacaoMapperTest {

    private CaraterDaInternacaoMapper caraterDaInternacaoMapper;

    @BeforeEach
    public void setUp() {
        caraterDaInternacaoMapper = new CaraterDaInternacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(caraterDaInternacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(caraterDaInternacaoMapper.fromId(null)).isNull();
    }
}
