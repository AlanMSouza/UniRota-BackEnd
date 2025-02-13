package br.com.fib.unirota.Carona.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CreateCaronaRequest(String localOrigem, LocalDate dataInicial, LocalDate dataFinal, String horarioSaida,
    String horarioRetorno, int numeroAssentos, Long usuarioId,
    boolean caronaRecorrente, BigDecimal preco, Long carroId, List<String> cidadesPassagem) {

}