package br.com.basis.madre.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CertidaoMapperTest {

    private CertidaoMapper certidaoMapper;

    @BeforeEach
    public void setUp() {
        certidaoMapper = new CertidaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(certidaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(certidaoMapper.fromId(null)).isNull();
    }
}
