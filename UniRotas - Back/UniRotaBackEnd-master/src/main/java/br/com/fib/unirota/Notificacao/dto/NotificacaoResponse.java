package br.com.fib.unirota.Notificacao.dto;

import java.time.LocalDateTime;

import br.com.fib.unirota.Notificacao.entity.Notificacao;
import br.com.fib.unirota.Notificacao.entity.TipoNotificacao;
import br.com.fib.unirota.Usuario.dto.UsuarioDTO;

public record NotificacaoResponse(Long id, TipoNotificacao tipoNotificacao, LocalDateTime dataCriacao,
    UsuarioDTO remetente, UsuarioDTO destinatario) {

  public NotificacaoResponse(Notificacao notificacao) {
    this(
        notificacao.getId(),
        notificacao.getTipoNotificacao(),
        notificacao.getDataCriacao(),
        new UsuarioDTO(notificacao.getRemetente()),
        new UsuarioDTO(notificacao.getDestinatario()));
  }
}
