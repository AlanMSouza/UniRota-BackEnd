package br.com.fib.unirota.Mensagem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fib.unirota.Mensagem.entity.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

  @Query("SELECT msg FROM Mensagem msg WHERE msg.conversa.id = :id ORDER BY msg.dataCriacao ASC")
  List<Mensagem> findAllByConversaId(Long id);
}
