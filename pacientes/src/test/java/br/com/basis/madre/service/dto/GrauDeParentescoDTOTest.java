package br.com.basis.madre.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class GrauDeParentescoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrauDeParentescoDTO.class);
        GrauDeParentescoDTO grauDeParentescoDTO1 = new GrauDeParentescoDTO();
        grauDeParentescoDTO1.setId(1L);
        GrauDeParentescoDTO grauDeParentescoDTO2 = new GrauDeParentescoDTO();
        assertThat(grauDeParentescoDTO1).isNotEqualTo(grauDeParentescoDTO2);
        grauDeParentescoDTO2.setId(grauDeParentescoDTO1.getId());
        assertThat(grauDeParentescoDTO1).isEqualTo(grauDeParentescoDTO2);
        grauDeParentescoDTO2.setId(2L);
        assertThat(grauDeParentescoDTO1).isNotEqualTo(grauDeParentescoDTO2);
        grauDeParentescoDTO1.setId(null);
        assertThat(grauDeParentescoDTO1).isNotEqualTo(grauDeParentescoDTO2);
    }
}
