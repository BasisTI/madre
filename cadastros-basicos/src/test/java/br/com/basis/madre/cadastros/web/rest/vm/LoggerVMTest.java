package br.com.basis.madre.cadastros.web.rest.vm;

import ch.qos.logback.classic.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by pedro-ramalho on 04/06/18.
 */

@RunWith(PowerMockRunner.class)
public class LoggerVMTest {

    @InjectMocks
    Logger logger = null;

    @Mock
    LoggerVM loggerVM;

    @Mock
    String name;

    @Mock
    String level;







    @Test
    public void loggerVMTest(){
        new LoggerVM(logger);
        new LoggerVM();
    }


    @Test
    public void setNameTest(){new LoggerVM().setName(name);}

    @Test
    public void getNameTest(){new LoggerVM().getName();}

    @Test
    public void getLevelTest(){new LoggerVM().getLevel();}

    @Test
    public void setLevelTest(){new LoggerVM().setLevel(level);}

    @Test
    public void toStringTest(){
        Assert.assertEquals("LoggerVM{" + "name='" + null + '\'' + ", level='" + null + '\'' + '}', new LoggerVM().toString());
    }


}

