package br.com.basis.madre.cadastros.config.audit;

import br.com.basis.madre.cadastros.domain.PersistentAuditEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.actuate.audit.AuditEvent;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AuditEventConverterTest {
    @InjectMocks
    AuditEventConverter auditEventConverter;

    @Mock
    Iterable<PersistentAuditEvent> persistentAuditEvents;


    @Test
    public void convertToAuditEventNullTest(){
        persistentAuditEvents = null;
        List<AuditEvent> test = auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    }


}
