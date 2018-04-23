package br.com.basis.madre.cadastros.cucumber.stepdefs;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CadastrosbasicosApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
