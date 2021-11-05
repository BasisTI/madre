package br.com.basis.madre.madreexames.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.madreexames.web.rest.TestUtil;

import java.util.UUID;

public class ProcessoAssincronoGradeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessoAssincronoGradeDTO.class);
        ProcessoAssincronoGradeDTO processoAssincronoGradeDTO1 = new ProcessoAssincronoGradeDTO();
        processoAssincronoGradeDTO1.setId(UUID.randomUUID().toString());
        ProcessoAssincronoGradeDTO processoAssincronoGradeDTO2 = new ProcessoAssincronoGradeDTO();
        assertThat(processoAssincronoGradeDTO1).isNotEqualTo(processoAssincronoGradeDTO2);
        processoAssincronoGradeDTO2.setId(processoAssincronoGradeDTO1.getId());
        assertThat(processoAssincronoGradeDTO1).isEqualTo(processoAssincronoGradeDTO2);
        processoAssincronoGradeDTO2.setId(UUID.randomUUID().toString());
        assertThat(processoAssincronoGradeDTO1).isNotEqualTo(processoAssincronoGradeDTO2);
        processoAssincronoGradeDTO1.setId(null);
        assertThat(processoAssincronoGradeDTO1).isNotEqualTo(processoAssincronoGradeDTO2);
    }
}
