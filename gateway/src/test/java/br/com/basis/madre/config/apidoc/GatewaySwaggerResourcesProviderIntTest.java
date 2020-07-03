package br.com.basis.madre.config.apidoc;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;

import br.com.basis.madre.MadregatewayApp;
import springfox.documentation.swagger.web.SwaggerResource;

@SpringBootTest(classes = MadregatewayApp.class)
public class GatewaySwaggerResourcesProviderIntTest {

    @Spy
    private RouteLocator routeLocator;

    private GatewaySwaggerResourcesProvider gatewaySwaggerResourcesProvider;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mockito.doReturn(Collections.singletonList(new Route("microservicox", "/microservicox/**", "/microservicox/**", "", false, null))).when(routeLocator).getRoutes();
        gatewaySwaggerResourcesProvider = new GatewaySwaggerResourcesProvider(routeLocator);
    }

    @Test
    public void get() {
        List<SwaggerResource> list = gatewaySwaggerResourcesProvider.get();
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

}