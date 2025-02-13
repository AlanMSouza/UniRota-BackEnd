package br.com.fib.unirota.Solicitacao.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fib.unirota.Carona.repository.CaronaRepository;
import br.com.fib.unirota.ExpoPushNotificacao.ExpoPushNotificacaoService;
import br.com.fib.unirota.Notificacao.entity.Notificacao;
import br.com.fib.unirota.Notificacao.entity.TipoNotificacao;
import br.com.fib.unirota.Notificacao.repository.NotificacaoRepository;
import br.com.fib.unirota.Solicitacao.dto.SolicitacaoDTO;
import br.com.fib.unirota.Solicitacao.dto.SolicitacaoRequest;
import br.com.fib.unirota.Solicitacao.entity.Solicitacao;
import br.com.fib.unirota.Solicitacao.entity.StatusSolicitacao;
import br.com.fib.unirota.Solicitacao.repository.SolicitacaoRepository;
import br.com.fib.unirota.Usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SolicitacaoService {

  private final SolicitacaoRepository solicitacaoRepository;
  private final UsuarioRepository usuarioRepository;
  private final CaronaRepository caronaRepository;
  private final NotificacaoRepository notificacaoRepository;
  private final ExpoPushNotificacaoService expoPushNotificacaoService;

  public SolicitacaoService(SolicitacaoRepository solicitacaoRepository, UsuarioRepository usuarioRepository,
      CaronaRepository caronaRepository, ExpoPushNotificacaoService notificacaoService,
      NotificacaoRepository notificacaoRepository) {
    this.solicitacaoRepository = solicitacaoRepository;
    this.usuarioRepository = usuarioRepository;
    this.caronaRepository = caronaRepository;
    this.notificacaoRepository = notificacaoRepository;
    this.expoPushNotificacaoService = notificacaoService;
  }

  public void novaSolicitacao(SolicitacaoRequest solicitacaoRequest) {
    var carona = caronaRepository.findById(solicitacaoRequest.caronaId())
        .orElseThrow(() -> new EntityNotFoundException("Carona not found"));
    var usuario = usuarioRepository.findById(solicitacaoRequest.usuarioId())
        .orElseThrow(() -> new EntityNotFoundException("Usuario not found"));

    var solicitacao = new Solicitacao();
    solicitacao.setUsuario(usuario);
    solicitacao.setCarona(carona);
    solicitacao.setStatusSolicitacao(StatusSolicitacao.PENDENTE);

    solicitacaoRepository.save(solicitacao);

    var notificacao = Notificacao.builder()
        .destinatario(carona.getUsuario())
        .remetente(usuario)
        .TipoNotificacao(TipoNotificacao.CARONA_SOLICITACAO)
        .build();

    notificacaoRepository.save(notificacao);

    expoPushNotificacaoService.sendPushNotification(carona.getUsuario().getExpoNotificationToken(),
        "Nova solicitação de carona", usuario.getFirstName()
            + " deseja participar da sua carona, entre no aplicativo para aceitar ou rejeitar o pedido.");
  }

  public List<SolicitacaoDTO> solicitacoesPendentes(Long caronaId) {
    return solicitacaoRepository.findAllSolicitacaoPendenteByCaronaId(caronaId).stream().map(SolicitacaoDTO::new)
        .collect(Collectors.toList());
  }

  public void aceitarSolicitacao(Long solicitacaoId) {
    var solicitacao = solicitacaoRepository.findById(solicitacaoId)
        .orElseThrow(() -> new EntityNotFoundException("Solicitacao não encontrada: " + solicitacaoId));
    var carona = caronaRepository.findById(solicitacao.getCarona().getId())
        .orElseThrow(() -> new EntityNotFoundException("Carona not found"));

    solicitacao.setStatusSolicitacao(StatusSolicitacao.APROVADO);
    carona.getPassageiros().add(solicitacao.getUsuario());

    caronaRepository.save(carona);
    solicitacaoRepository.save(solicitacao);

    expoPushNotificacaoService.sendPushNotification(solicitacao.getUsuario().getExpoNotificationToken(),
        "Solicitação aprovada", carona.getUsuario().getFirstName()
            + " aceitou a sua solicitação para participar da carona.");
  }

  public void recusarSolicitacao(Long solicitacaoId) {
    var solicitacao = solicitacaoRepository.findById(solicitacaoId)
        .orElseThrow(() -> new EntityNotFoundException("Solicitacao não encontrada: " + solicitacaoId));
    var carona = caronaRepository.findById(solicitacao.getCarona().getId())
        .orElseThrow(() -> new EntityNotFoundException("Carona not found"));

    solicitacao.setStatusSolicitacao(StatusSolicitacao.RECUSADO);

    solicitacaoRepository.save(solicitacao);

    expoPushNotificacaoService.sendPushNotification(solicitacao.getUsuario().getExpoNotificationToken(),
        "Solicitação recusada", carona.getUsuario().getFirstName()
            + " recusou a sua solicitação para participar da carona.");
  }

}
