package com.exemplo.clientes.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exemplo.clientes.domain.Cliente;
import com.exemplo.clientes.service.ClienteService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

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
    void deveRetornarListaVaziaQuandoNaoHaClientes() throws Exception {
        when(service.listar()).thenReturn(List.of());

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
