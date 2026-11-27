package com.exemplo.clientes.api;

public record ErroResponse(int status, String error, String message) {
}
