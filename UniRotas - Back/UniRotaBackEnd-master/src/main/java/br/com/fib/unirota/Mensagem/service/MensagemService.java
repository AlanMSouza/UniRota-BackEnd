package br.com.fib.unirota.Mensagem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusher.rest.Pusher;

import br.com.fib.unirota.Conversa.repository.ConversaRepository;
import br.com.fib.unirota.ExpoPushNotificacao.ExpoPushNotificacaoService;
import br.com.fib.unirota.Mensagem.dto.EnviarMensagemRequest;
import br.com.fib.unirota.Mensagem.dto.MensagemResponse;
import br.com.fib.unirota.Mensagem.entity.Mensagem;
import br.com.fib.unirota.Mensagem.repository.MensagemRepository;
import br.com.fib.unirota.Usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MensagemService {

  private final MensagemRepository mensagemRepository;
  private final ConversaRepository conversaRepository;
  private final UsuarioRepository usuarioRepository;
  private final ExpoPushNotificacaoService notificationService;

  @Autowired
  private Pusher pusher;

  public MensagemService(MensagemRepository mensagemRepository, ConversaRepository conversaRepository,
      UsuarioRepository usuarioRepository, ExpoPushNotificacaoService notificationService) {
    this.mensagemRepository = mensagemRepository;
    this.conversaRepository = conversaRepository;
    this.usuarioRepository = usuarioRepository;
    this.notificationService = notificationService;
  }

  public void enviarMensagem(EnviarMensagemRequest enviarMensagemRequest) {
    var conversa = conversaRepository.findById(enviarMensagemRequest.conversaId())
        .orElseThrow(() -> new EntityNotFoundException());
    var usuario = usuarioRepository.findById(enviarMensagemRequest.usuarioRemetente())
        .orElseThrow(() -> new EntityNotFoundException());

    var mensagem = Mensagem.builder().corpo(enviarMensagemRequest.corpo()).usuarioRemetente(usuario).conversa(conversa)
        .build();

    var novaMensagem = mensagemRepository.save(mensagem);

    conversa.getMensagens().add(novaMensagem);
    conversa.setDataUltimaMensagem(LocalDateTime.now());

    conversaRepository.save(conversa);

    conversa.getUsuarios().stream()
        .filter(user -> !user.getId().equals(usuario.getId()))
        .forEach(user -> notificationService.sendPushNotification(user.getExpoNotificationToken(),
            usuario.getFirstName() + " " + usuario.getLastName(),
            novaMensagem.getCorpo()));

    if (novaMensagem.getListaVisualizados() == null)
      novaMensagem.setListaVisualizados(new ArrayList<>());
    if (novaMensagem.getImagem() == null)
      novaMensagem.setImagem("");

    pusher.trigger(enviarMensagemRequest.conversaId().toString(), "messages:new", new MensagemResponse(novaMensagem));

    var mensagensResponse = conversa.getMensagens().stream().map(MensagemResponse::new).collect(Collectors.toList());

    Map<String, Object> data = new HashMap<>();
    data.put("conversaId", conversa.getId());
    data.put("mensagens", mensagensResponse);

    conversa.getUsuarios().forEach(user -> {
      pusher.trigger(user.getUsername(), "conversation:update", data);
    });
  }

  public List<MensagemResponse> buscarMensagensByConversaId(Long conversaId) {
    return mensagemRepository.findAllByConversaId(conversaId).stream().map(MensagemResponse::new)
        .collect(Collectors.toList());
  }

}
