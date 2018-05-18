package br.com.basis.madre.cadastros.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersistentAuditEventTest {
    @InjectMocks
    PersistentAuditEvent persistentAuditEvent;

    @Test
    public void getIdTest(){
        persistentAuditEvent.getId();
    }

    @Test
    public void setIdTest(){
        persistentAuditEvent.setId(0L);
    }

    @Test
    public void getPrincipalTest(){
        persistentAuditEvent.getPrincipal();
    }

    @Test
    public void setPrincipalTest(){
        persistentAuditEvent.setPrincipal("Test");
    }

    @Test
    public void getAuditEventDateTest(){
        persistentAuditEvent.getAuditEventDate();
    }

    @Test
    public void setAuditEventDateTest(){
        persistentAuditEvent.setAuditEventDate(null);
    }
    @Test
    public void getAuditEventTypeTest(){
        persistentAuditEvent.getAuditEventType();
    }

    @Test
    public void setAuditEventTypeTest(){
        persistentAuditEvent.setAuditEventType(null);
    }

    @Test
    public void getDataTest(){
        persistentAuditEvent.getData();
    }

    @Test
    public void setDataTest(){
        persistentAuditEvent.setData(null);
    }




}
