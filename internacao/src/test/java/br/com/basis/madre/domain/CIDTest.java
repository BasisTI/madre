package br.com.basis.madre.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.basis.madre.web.rest.TestUtil;

public class CIDTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CID.class);
        CID cID1 = new CID();
        cID1.setId(1L);
        CID cID2 = new CID();
        cID2.setId(cID1.getId());
        assertThat(cID1).isEqualTo(cID2);
        cID2.setId(2L);
        assertThat(cID1).isNotEqualTo(cID2);
        cID1.setId(null);
        assertThat(cID1).isNotEqualTo(cID2);
    }
}
