package br.com.fib.unirota.Notificacao.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fib.unirota.Notificacao.dto.NotificacaoResponse;
import br.com.fib.unirota.Notificacao.repository.NotificacaoRepository;

@Service
public class NotificacaoService {

  private final NotificacaoRepository notificacaoRepository;

  public NotificacaoService(NotificacaoRepository notificacaoRepository) {
    this.notificacaoRepository = notificacaoRepository;
  }

  public List<NotificacaoResponse> findAll() {
    var notificacaoes = notificacaoRepository.findAll(Sort.by(Sort.Direction.ASC, "dataCriacao"));
    return notificacaoes.stream().map(NotificacaoResponse::new).collect(Collectors.toList());
  }
}
