package br.com.basis.madre.cadastros.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class UploadFileTest {
    @InjectMocks
    private UploadFile uploadFile;

    @Test
    public void getIdTest(){
        uploadFile.getId();
    }

    @Test
    public void setIdTest(){
        uploadFile.setId(0L);
    }

    @Test
    public void getOriginalNameTest(){
        uploadFile.getOriginalName();
    }

    @Test
    public void setOriginalNameTest(){
        uploadFile.setOriginalName("name");
    }

    @Test
    public void getFileNameTest(){ uploadFile.getFilename(); }

    @Test
    public void setFileNameTest(){ uploadFile.setFilename("test"); }

    @Test
    public void getDateOfTest(){ uploadFile.getDateOf(); }

    @Test
    public void setDateOfTest(){
        Date date = new Date();
        uploadFile.setDateOf(date);
    }

    @Test
    public void getSizeOfTest(){ uploadFile.getSizeOf(); }

    @Test
    public void setSizeOfTest(){ uploadFile.setSizeOf(1); }

    @Test
    public void getProcessTypeTest(){ uploadFile.getProcessType(); }

    @Test
    public void setProcessTypeTest(){ uploadFile.setProcessType(1); }


}
