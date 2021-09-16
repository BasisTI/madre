package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GradeDeAgendamentoMapperTest {

    private GradeDeAgendamentoMapper gradeDeAgendamentoMapper;

    @BeforeEach
    public void setUp() {
        gradeDeAgendamentoMapper = new GradeDeAgendamentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(gradeDeAgendamentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gradeDeAgendamentoMapper.fromId(null)).isNull();
    }
}
