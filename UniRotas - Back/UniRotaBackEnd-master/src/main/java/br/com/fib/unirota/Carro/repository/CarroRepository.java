package br.com.fib.unirota.Carro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fib.unirota.Carro.dto.CarroDTO;
import br.com.fib.unirota.Carro.entity.Carro;

public interface CarroRepository extends JpaRepository<Carro, Long> {
  @Query("SELECT new br.com.fib.unirota.Carro.dto.CarroDTO(c.id, c.placa, c.cor, c.modelo) FROM Carro c WHERE c.usuario.id = :usuarioId")
  List<CarroDTO> findAllByUsuarioId(Long usuarioId);
}
