package br.com.fib.unirota.Conversa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusher.rest.Pusher;

import br.com.fib.unirota.Conversa.dto.ConversaResponse;
import br.com.fib.unirota.Conversa.dto.CreateConversaRequest;
import br.com.fib.unirota.Conversa.dto.VisualizouMensagemRequest;
import br.com.fib.unirota.Conversa.entity.Conversa;
import br.com.fib.unirota.Conversa.repository.ConversaRepository;
import br.com.fib.unirota.Mensagem.dto.MensagemResponse;
import br.com.fib.unirota.Mensagem.repository.MensagemRepository;
import br.com.fib.unirota.Usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ConversaService {
  private final ConversaRepository conversaRepository;
  private final UsuarioRepository usuarioRepository;
  private final MensagemRepository mensagemRepository;

  @Autowired
  private Pusher pusher;

  public ConversaService(ConversaRepository conversaRepository, UsuarioRepository usuarioRepository,
      MensagemRepository mensagemRepository) {
    this.conversaRepository = conversaRepository;
    this.usuarioRepository = usuarioRepository;
    this.mensagemRepository = mensagemRepository;
  }

  public ConversaResponse create(CreateConversaRequest createConversaRequest) {
    var usuarios = usuarioRepository.findAllById(createConversaRequest.usuarioIds());

    if (usuarios.isEmpty()) {
      throw new EntityNotFoundException();
    }

    if (!createConversaRequest.isGroup()) {
      var existConversa = conversaRepository.findByUsuarios(usuarios.stream().map(usuario -> usuario.getId()).toList());

      if (existConversa.isPresent()) {
        return new ConversaResponse(existConversa.get());
      }
    }

    var conversa = Conversa.builder().isGroup(createConversaRequest.isGroup()).nome(createConversaRequest.nome())
        .usuarios(usuarios)
        .build();

    var conversaCriada = conversaRepository.save(conversa);

    conversaCriada.getUsuarios().forEach(usuario -> {
      pusher.trigger(usuario.getUsername(), "conversation:new", new ConversaResponse(conversaCriada));
    });

    return new ConversaResponse(conversaCriada);
  }

  public List<ConversaResponse> findAllByUsuarioId(Long id) {
    return conversaRepository.findByUsuarioId(id).stream().map(conversa -> new ConversaResponse(conversa))
        .collect(Collectors.toList());
  }

  public void visualizarMensagem(Long conversaId, VisualizouMensagemRequest visualizouMensagemRequest) {
    var conversa = conversaRepository.findById(conversaId)
        .orElseThrow(() -> new EntityNotFoundException("Conversa não encontrada com ID: " + conversaId));

    var usuario = usuarioRepository.findById(visualizouMensagemRequest.usuarioId())
        .orElseThrow(() -> new EntityNotFoundException(
            "Usuário não encontrado com ID: " + visualizouMensagemRequest.usuarioId()));

    var mensagensNaoRemetente = conversa.getMensagens().stream()
        .filter(mensagem -> !mensagem.getUsuarioRemetente().getId().equals(visualizouMensagemRequest.usuarioId()))
        .filter(mensagem -> !mensagem.getListaVisualizados().contains(usuario))
        .collect(Collectors.toList());

    if (!mensagensNaoRemetente.isEmpty()) {
      mensagensNaoRemetente.forEach(mensagem -> mensagem.getListaVisualizados().add(usuario));

      mensagemRepository.saveAll(mensagensNaoRemetente);

      var mensagensAtualizadas = conversa.getMensagens();

      mensagensAtualizadas.forEach(mensagem -> {
        if (mensagensNaoRemetente.contains(mensagem)) {
          mensagem.getListaVisualizados().add(usuario);
        }
      });

      Map<String, Object> data = new HashMap<>();
      data.put("conversaId", conversa.getId());
      data.put("mensagens", mensagensAtualizadas.stream().map(MensagemResponse::new).collect(Collectors.toList()));

      conversa.getUsuarios().forEach(user -> pusher.trigger(user.getUsername(), "conversation:update", data));
    }
  }

}
