package br.com.basis.madre.cadastros.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.time.LocalDate;

import static br.com.basis.madre.cadastros.util.MadreUtil.getReportFooter;
import static br.com.basis.madre.cadastros.util.MadreUtil.localDateTimeEmString;


@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ LocalDate.class })
//@RunWith(PowerMockRunner.class)

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
        String test = madreUtil.transformaLocalDateTimeEmString(LocalDate.now());
        test = madreUtil.transformaLocalDateTimeEmString(null);
    }

}
