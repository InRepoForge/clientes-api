package com.exemplo.clientes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.exemplo.clientes.domain.Cliente;
import com.exemplo.clientes.repository.ClienteRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    void deveListarClientesQuandoExistemRegistros() {
        var ana = new Cliente("Ana Souza", "11122233344", "ana@example.com");
        when(repository.findAll()).thenReturn(List.of(ana));

        assertThat(service.listar()).containsExactly(ana);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaClientes() {
        when(repository.findAll()).thenReturn(List.of());

        assertThat(service.listar()).isEmpty();
    }
}
