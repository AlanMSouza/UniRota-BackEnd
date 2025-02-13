package br.com.fib.unirota.Conversa.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.fib.unirota.Conversa.entity.Conversa;
import br.com.fib.unirota.Mensagem.dto.MensagemResponse;
import br.com.fib.unirota.Usuario.dto.UsuarioDTO;

public record ConversaResponse(Long id, String nome, LocalDateTime dataCriacao, List<UsuarioDTO> usuarios,
    List<MensagemResponse> mensagens,
    LocalDateTime dataUltimaMensagem,
    boolean isGroup) {

  public ConversaResponse(Conversa conversa) {
    this(conversa.getId(),
        conversa.getNome(),
        conversa.getDataCriacao(),
        conversa.getUsuarios().stream().map(UsuarioDTO::new).collect(Collectors.toList()),
        Optional.ofNullable(conversa.getMensagens()).orElse(Collections.emptyList()).stream()
            .map(MensagemResponse::new)
            .collect(Collectors.toList()),
        conversa.getDataUltimaMensagem(),
        conversa.isGroup());
  }

}
