package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class ConvenioDeSaudeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConvenioDeSaude.class);
        ConvenioDeSaude convenioDeSaude1 = new ConvenioDeSaude();
        convenioDeSaude1.setId(1L);
        ConvenioDeSaude convenioDeSaude2 = new ConvenioDeSaude();
        convenioDeSaude2.setId(convenioDeSaude1.getId());
        assertThat(convenioDeSaude1).isEqualTo(convenioDeSaude2);
        convenioDeSaude2.setId(2L);
        assertThat(convenioDeSaude1).isNotEqualTo(convenioDeSaude2);
        convenioDeSaude1.setId(null);
        assertThat(convenioDeSaude1).isNotEqualTo(convenioDeSaude2);
    }
}
