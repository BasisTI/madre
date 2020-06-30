package br.com.basis.madre.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.basis.madre.MadregatewayApp;
import br.gov.nuvem.comum.microsservico.web.rest.errors.ExceptionTranslator;

@SpringBootTest(classes = MadregatewayApp.class)
public class GatewayResourceIntTest {

    @Spy
    private RouteLocator routeLocator;

    @Autowired
    private DiscoveryClient discoveryClient;

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

        Mockito.doReturn(Collections.singletonList(new Route("1", "/teste", "/teste", "teste", true, new HashSet<>()))).when(routeLocator).getRoutes();

        GatewayResource resource = new GatewayResource(routeLocator, discoveryClient);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(resource).setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator).setMessageConverters(jacksonMessageConverter, new ResourceHttpMessageConverter()).build();
    }

    @Test
    public void getRoutes() throws Exception {
        restMockMvc.perform(get("/api/gateway/routes")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(jsonPath("$.[*].path").exists())
                    .andExpect(jsonPath("$.[*].serviceId").exists())
                    .andExpect(jsonPath("$.[*].serviceInstances").exists());

    }

}
