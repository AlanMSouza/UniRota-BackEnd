package br.com.fib.unirota.Carona.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fib.unirota.Carona.entity.Carona;

public interface CaronaRepository extends JpaRepository<Carona, Long> {
  @Query("SELECT c FROM Carona c WHERE c.finalizado = false")
  List<Carona> findAllCaronas();

  @Query("SELECT c FROM Carona c WHERE (c.usuario.id = :usuarioId AND c.finalizado = false) OR (:usuarioId IN (SELECT p.id FROM c.passageiros p) AND c.dataFinal >= CURRENT_DATE AND c.finalizado = false)")
  List<Carona> findAllByUsuarioId(Long usuarioId);

  @Query("SELECT c " +
      "FROM Carona c " +
      "JOIN c.cidadesPassagem cidades " +
      "WHERE cidades = :cidade AND c.assentos > SIZE(c.passageiros) AND c.finalizado = false")
  List<Carona> findAllByCity(@Param("cidade") String cidade);
}
