package br.com.fib.unirota.Carro.dto;

import br.com.fib.unirota.Carro.entity.Carro;

public record CarroDTO(Long id, String placa, String cor, String modelo) {
  public CarroDTO(Carro carro) {
    this(
        carro.getId(),
        carro.getPlaca(),
        carro.getCor(),
        carro.getModelo());
  }
}
