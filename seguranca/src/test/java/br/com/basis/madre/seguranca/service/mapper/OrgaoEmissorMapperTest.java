package br.com.basis.madre.seguranca.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrgaoEmissorMapperTest {

    private OrgaoEmissorMapper orgaoEmissorMapper;

    @BeforeEach
    public void setUp() {
        orgaoEmissorMapper = new OrgaoEmissorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(orgaoEmissorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(orgaoEmissorMapper.fromId(null)).isNull();
    }
}
