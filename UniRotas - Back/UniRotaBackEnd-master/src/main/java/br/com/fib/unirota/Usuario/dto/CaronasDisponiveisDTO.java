package br.com.fib.unirota.Usuario.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.fib.unirota.Carona.entity.Carona;
import br.com.fib.unirota.Carro.dto.CarroDTO;

public record CaronasDisponiveisDTO(Long id, String localOrigem, BigDecimal preco, LocalDate dataInicial,
    LocalDate dataFinal, String horarioSaida, String horarioRetorno, int assentos, CarroDTO carro,
    boolean caronaRecorrente, UsuarioDTO usuario) {
  public CaronasDisponiveisDTO(Carona carona) {
    this(
        carona.getId(),
        carona.getLocalOrigem(),
        carona.getPreco(),
        carona.getDataInicial(),
        carona.getDataFinal(),
        carona.getHorarioSaida(),
        carona.getHorarioRetorno(),
        carona.getAssentos(),
        new CarroDTO(carona.getCarro()),
        carona.isCaronaRecorrente(),
        new UsuarioDTO(carona.getUsuario()));
  }
}
