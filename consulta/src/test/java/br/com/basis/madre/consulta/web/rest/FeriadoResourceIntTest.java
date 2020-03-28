package br.com.basis.madre.consulta.web.rest;

import br.com.basis.madre.consulta.ConsultaApp;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsultaApp.class)
@Transactional
@WithMockUser
public class FeriadoResourceIntTest {

}
