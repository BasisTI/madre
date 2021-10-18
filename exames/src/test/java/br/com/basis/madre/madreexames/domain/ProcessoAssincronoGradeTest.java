package br.com.basis.madre.madreexames.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

public class ProcessoAssincronoGradeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessoAssincronoGrade.class);
        ProcessoAssincronoGrade processoAssincronoGrade1 = new ProcessoAssincronoGrade();
        processoAssincronoGrade1.setId(processoAssincronoGrade1.getId());
        ProcessoAssincronoGrade processoAssincronoGrade2 = new ProcessoAssincronoGrade();
        processoAssincronoGrade2.setId(processoAssincronoGrade1.getId());
        assertThat(processoAssincronoGrade1).isEqualTo(processoAssincronoGrade2);
        processoAssincronoGrade2.setId(processoAssincronoGrade2.getId());
        assertThat(processoAssincronoGrade1).isNotEqualTo(processoAssincronoGrade2);
        processoAssincronoGrade1.setId(null);
        assertThat(processoAssincronoGrade1).isNotEqualTo(processoAssincronoGrade2);
    }
}
