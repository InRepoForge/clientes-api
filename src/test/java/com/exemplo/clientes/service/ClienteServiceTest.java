package com.exemplo.clientes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    void deveSalvarClienteQuandoDadosValidos() {
        when(repository.save(any(Cliente.class))).thenAnswer(invocacao -> invocacao.getArgument(0));

        var criado = service.criar("Diego Rocha", "12345678901", "diego@example.com");

        assertThat(criado.getNome()).isEqualTo("Diego Rocha");
        assertThat(criado.getCpf()).isEqualTo("12345678901");
        assertThat(criado.getEmail()).isEqualTo("diego@example.com");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaClientes() {
        when(repository.findAll()).thenReturn(List.of());

        assertThat(service.listar()).isEmpty();
    }
}
