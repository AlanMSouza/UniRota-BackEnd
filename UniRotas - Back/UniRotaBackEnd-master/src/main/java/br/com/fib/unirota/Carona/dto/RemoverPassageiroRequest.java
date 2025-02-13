package br.com.fib.unirota.Carona.dto;

public record RemoverPassageiroRequest(Long usuarioId, Long caronaId, boolean removido) {
}
