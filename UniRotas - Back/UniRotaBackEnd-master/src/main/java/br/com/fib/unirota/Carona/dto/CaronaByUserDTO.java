package br.com.fib.unirota.Carona.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.fib.unirota.Carona.entity.Carona;
import br.com.fib.unirota.Carro.dto.CarroDTO;
import br.com.fib.unirota.Solicitacao.entity.StatusSolicitacao;
import br.com.fib.unirota.Usuario.dto.UsuarioDTO;

public record CaronaByUserDTO(Long id, String localOrigem, BigDecimal preco, LocalDate dataInicial,
    LocalDate dataFinal, String horarioSaida, String horarioRetorno,
    int assentos,
    UsuarioDTO usuario, boolean caronaRecorrente, CarroDTO carro, List<UsuarioDTO> listaUsuariosPendentes,
    List<UsuarioDTO> listaPassageiros) {

  public CaronaByUserDTO(Carona carona) {
    this(
        carona.getId(),
        carona.getLocalOrigem(),
        carona.getPreco(),
        carona.getDataInicial(),
        carona.getDataFinal(),
        carona.getHorarioSaida(),
        carona.getHorarioRetorno(),
        carona.getAssentos(),
        new UsuarioDTO(carona.getUsuario()),
        carona.isCaronaRecorrente(),
        new CarroDTO(carona.getCarro()),
        carona.getSolicitoes()
            .stream()
            .filter(solicitacao -> solicitacao.getStatusSolicitacao().equals(StatusSolicitacao.PENDENTE))
            .map(solicitacao -> new UsuarioDTO(solicitacao.getUsuario()))
            .collect(Collectors.toList()),
        carona.getPassageiros().stream().map(passageiro -> new UsuarioDTO(passageiro))
            .collect(Collectors.toList()));
  }
}
