package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class GrauDeParentescoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrauDeParentesco.class);
        GrauDeParentesco grauDeParentesco1 = new GrauDeParentesco();
        grauDeParentesco1.setId(1L);
        GrauDeParentesco grauDeParentesco2 = new GrauDeParentesco();
        grauDeParentesco2.setId(grauDeParentesco1.getId());
        assertThat(grauDeParentesco1).isEqualTo(grauDeParentesco2);
        grauDeParentesco2.setId(2L);
        assertThat(grauDeParentesco1).isNotEqualTo(grauDeParentesco2);
        grauDeParentesco1.setId(null);
        assertThat(grauDeParentesco1).isNotEqualTo(grauDeParentesco2);
    }
}
