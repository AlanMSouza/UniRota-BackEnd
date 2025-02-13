package br.com.fib.unirota.Carro.dto;

public record CreateCarroRequest(String placa, String cor, String modelo, Long usuarioId) {
}
