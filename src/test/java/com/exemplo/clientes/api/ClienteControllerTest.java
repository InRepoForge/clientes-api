package com.exemplo.clientes.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exemplo.clientes.domain.Cliente;
import com.exemplo.clientes.service.ClienteService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    private static final String JSON_VALIDO =
            "{\"nome\":\"Diego Rocha\",\"cpf\":\"12345678901\",\"email\":\"diego@example.com\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService service;

    @Test
    void deveListarClientesQuandoGetClientes() throws Exception {
        when(service.listar()).thenReturn(List.of(new Cliente("Ana Souza", "11122233344", "ana@example.com")));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Ana Souza"))
                .andExpect(jsonPath("$[0].cpf").value("11122233344"))
                .andExpect(jsonPath("$[0].email").value("ana@example.com"));
    }

    @Test
    void deveCriarClienteQuandoDadosValidos() throws Exception {
        when(service.criar("Diego Rocha", "12345678901", "diego@example.com"))
                .thenReturn(new Cliente("Diego Rocha", "12345678901", "diego@example.com"));

        mockMvc.perform(post("/clientes").contentType(MediaType.APPLICATION_JSON).content(JSON_VALIDO))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.nome").value("Diego Rocha"))
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.email").value("diego@example.com"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"cpf\":\"12345678901\",\"email\":\"a@example.com\"}",
            "{\"nome\":\"\",\"cpf\":\"12345678901\",\"email\":\"a@example.com\"}",
            "{\"nome\":\"Fulano\",\"cpf\":\"1234567890\",\"email\":\"a@example.com\"}",
            "{\"nome\":\"Fulano\",\"cpf\":\"123456789012\",\"email\":\"a@example.com\"}",
            "{\"nome\":\"Fulano\",\"cpf\":\"123.456.789-01\",\"email\":\"a@example.com\"}",
            "{\"nome\":\"Fulano\",\"cpf\":\"1234567890a\",\"email\":\"a@example.com\"}",
            "{\"nome\":\"Fulano\",\"cpf\":\"12345678901\",\"email\":\"nao-e-email\"}",
            "{\"nome\":\"Fulano\",\"cpf\":\"12345678901\"}"
    })
    void deveRetornar400QuandoDadosInvalidos(String json) throws Exception {
        mockMvc.perform(post("/clientes").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaClientes() throws Exception {
        when(service.listar()).thenReturn(List.of());

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
