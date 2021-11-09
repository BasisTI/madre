package br.com.basis.madre.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.basis.madre.MadregatewayApp;
import br.com.basis.madre.controller.UserJwtController;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;

@SpringBootTest(classes = MadregatewayApp.class)
public class UserDetailsResourceIntTest {

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        UserJwtController resource = new UserJwtController();
        this.restMockMvc = MockMvcBuilders.standaloneSetup(resource).setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator).setMessageConverters(jacksonMessageConverter, new ResourceHttpMessageConverter()).build();
    }

    @Test
    @WithMockUser
    public void getUserDetails() throws Exception {
        restMockMvc.perform(get("/api/user/details")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

}
