package com.exemplo.clientes.api;

import com.exemplo.clientes.domain.Cliente;

public record ClienteResponse(Long id, String nome, String cpf, String email) {

    public static ClienteResponse de(Cliente cliente) {
        return new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getEmail());
    }
}
