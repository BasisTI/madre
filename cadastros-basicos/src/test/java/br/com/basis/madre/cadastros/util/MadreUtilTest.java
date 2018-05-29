package br.com.basis.madre.cadastros.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import java.time.LocalDate;

import static br.com.basis.madre.cadastros.util.MadreUtil.getReportFooter;
import static br.com.basis.madre.cadastros.util.MadreUtil.localDateTimeEmString;
import static br.com.basis.madre.cadastros.util.MadreUtil.transformaLocalDateTimeEmString;

@RunWith(MockitoJUnitRunner.class)
public class MadreUtilTest {
    @InjectMocks
    MadreUtil madreUtil;



    @Test
    public void getReportFooterTest() {
        String test = getReportFooter();
    }


    @Test
    public void localDateTimeEmStringTest(){

        String test = localDateTimeEmString(null);
    }

    @Test
    public void transformaDataLocalEmStringTest(){
        String test = transformaLocalDateTimeEmString(LocalDate.now());
        test = transformaLocalDateTimeEmString(null);
    }

}
