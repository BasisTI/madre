package br.com.basis.madre.cadastros.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractAuditingEntityTest {
    AbstractAuditingEntity test = new AbstractAuditingEntity() {
    };

    @Test
    public void getCreatedByTest(){
        test.getCreatedBy();
    }

    @Test
    public void setCreatedByTest(){
        test.setCreatedBy("teste");
    }


    @Test
    public void getCreatedDateTest(){
        test.getCreatedDate();
    }

    @Test
    public void setCreatedDateTest(){
        test.setCreatedDate(null);
    }


    @Test
    public void getLastModifiedByTest(){
        test.getLastModifiedBy();
    }

    @Test
    public void setLastModifiedBy(){
        test.setLastModifiedBy("teste");
    }


    @Test
    public void getLastModifiedDateTest(){
        test.getLastModifiedDate();
    }

    @Test
    public void setLastModifiedDate(){
        test.setLastModifiedDate(null);
    }
}
