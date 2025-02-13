package br.com.fib.unirota.Mensagem.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.fib.unirota.Mensagem.entity.Mensagem;
import br.com.fib.unirota.Usuario.dto.UsuarioDTO;

public record MensagemResponse(Long id, String corpo, List<UsuarioDTO> listaVisualizados, Long conversaId,
    Long usuarioRemetenteId, LocalDateTime dataCriacao) {
  public MensagemResponse(Mensagem mensagem) {
    this(
        mensagem.getId(),
        mensagem.getCorpo(),
        mensagem.getListaVisualizados().stream().map(UsuarioDTO::new).collect(Collectors.toList()),
        mensagem.getConversa().getId(),
        mensagem.getUsuarioRemetente().getId(),
        mensagem.getDataCriacao());
  }
}
