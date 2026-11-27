package com.exemplo.clientes.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.exemplo.clientes.domain.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    @Test
    void deveGerarIdQuandoSalvaCliente() {
        var salvo = repository.save(new Cliente("Ana Souza", "11122233344", "ana@example.com"));

        assertThat(salvo.getId()).isNotNull();
    }

    @Test
    void deveListarClientesSalvosQuandoBuscaTodos() {
        repository.save(new Cliente("Ana Souza", "11122233344", "ana@example.com"));
        repository.save(new Cliente("Bruno Lima", "55566677788", "bruno@example.com"));

        assertThat(repository.findAll()).hasSize(2);
    }
}
