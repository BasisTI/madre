package br.com.basis.madre.pacientes.cucumber.stepdefs;

import br.com.basis.madre.pacientes.PacientesApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = PacientesApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
