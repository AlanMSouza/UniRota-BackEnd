package br.com.fib.unirota.Conversa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fib.unirota.Conversa.entity.Conversa;

public interface ConversaRepository extends JpaRepository<Conversa, Long> {
  @Query("SELECT cv FROM Conversa cv JOIN cv.usuarios u WHERE u.id = :id")
  List<Conversa> findByUsuarioId(Long id);

  @Query("SELECT c FROM Conversa c JOIN c.usuarios u WHERE u IN (:usuarioIds)"
      + " GROUP BY c " +
      " HAVING COUNT(u) = 2 ")
  Optional<Conversa> findByUsuarios(List<Long> usuarioIds);
}
