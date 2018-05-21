package br.com.basis.madre.cadastros.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDate;

import static br.com.basis.madre.cadastros.util.MadreUtil.getReportFooter;
import static br.com.basis.madre.cadastros.util.MadreUtil.localDateTimeEmString;
import static br.com.basis.madre.cadastros.util.MadreUtil.transformaLocalDateTimeEmString;

@RunWith(PowerMockRunner.class)
public class MadreUtilTest {
    @InjectMocks
    MadreUtil madreUtil;

    @Mock
    LocalDate localDate;


    @Test
    public void getReportFooterTest() {
        PowerMockito.mockStatic(MadreUtil.class);
        String test = getReportFooter();
    }

    @Test
    public void transformaLocalDateTimeStringTest(){
       // PowerMockito.mockStatic(MadreUtil.class);
        String test = transformaLocalDateTimeEmString(null);
        test = transformaLocalDateTimeEmString(localDate);
    }

    @Test
    public void localDateTimeEmStringTest(){
        String test = localDateTimeEmString(null);
    }
}
