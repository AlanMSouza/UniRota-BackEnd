package br.com.fib.unirota.Solicitacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fib.unirota.Solicitacao.entity.Solicitacao;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
  @Query("SELECT s FROM Solicitacao s WHERE s.statusSolicitacao = PENDENTE AND s.carona.id = :caronaId")
  List<Solicitacao> findAllSolicitacaoPendenteByCaronaId(Long caronaId);
}
