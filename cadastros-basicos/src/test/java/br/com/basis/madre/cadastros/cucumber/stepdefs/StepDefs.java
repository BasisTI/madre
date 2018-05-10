package br.com.basis.madre.cadastros.cucumber.stepdefs;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CadastrosbasicosApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
