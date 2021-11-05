package br.com.basis.madre.madreexames.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GradeAgendamentoExameMapperTest {

    private GradeAgendamentoExameMapper gradeAgendamentoExameMapper;

    @BeforeEach
    public void setUp() {
        gradeAgendamentoExameMapper = new GradeAgendamentoExameMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(gradeAgendamentoExameMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gradeAgendamentoExameMapper.fromId(null)).isNull();
    }
}
