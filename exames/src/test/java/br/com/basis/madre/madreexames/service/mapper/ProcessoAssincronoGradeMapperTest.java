package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProcessoAssincronoGradeMapperTest {

    private ProcessoAssincronoGradeMapper processoAssincronoGradeMapper;

    @BeforeEach
    public void setUp() {
        processoAssincronoGradeMapper = new ProcessoAssincronoGradeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "506a8c8b-9f05-45d0-bddb-ce996c282729";
        assertThat(processoAssincronoGradeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(processoAssincronoGradeMapper.fromId(null)).isNull();
    }
}
