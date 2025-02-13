package br.com.fib.unirota.Carona.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fib.unirota.Carona.dto.CaronaByUserDTO;
import br.com.fib.unirota.Carona.dto.CaronaDTO;
import br.com.fib.unirota.Carona.dto.CreateCaronaRequest;
import br.com.fib.unirota.Carona.dto.RemoverPassageiroRequest;
import br.com.fib.unirota.Carona.entity.Carona;
import br.com.fib.unirota.Carona.repository.CaronaRepository;
import br.com.fib.unirota.Carro.entity.Carro;
import br.com.fib.unirota.Carro.repository.CarroRepository;
import br.com.fib.unirota.Conversa.service.ConversaService;
import br.com.fib.unirota.ExpoPushNotificacao.ExpoPushNotificacaoService;
import br.com.fib.unirota.Usuario.dto.CaronasDisponiveisDTO;
import br.com.fib.unirota.Usuario.entity.Usuario;
import br.com.fib.unirota.Usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CaronaService {
  private final CaronaRepository caronaRepository;
  private final CarroRepository carroRepository;
  private final UsuarioRepository userRepository;
  private final ExpoPushNotificacaoService expoPushNotificacaoService;
  private final ConversaService conversaService;

  public CaronaService(CaronaRepository caronaRepository, CarroRepository carroRepository,
      UsuarioRepository userRepository, ExpoPushNotificacaoService expoPushNotificacaoService,
      ConversaService conversaService) {
    this.caronaRepository = caronaRepository;
    this.carroRepository = carroRepository;
    this.userRepository = userRepository;
    this.expoPushNotificacaoService = expoPushNotificacaoService;
    this.conversaService = conversaService;
  }

  public void createCarona(CreateCaronaRequest createCaronaRequest) {
    Usuario usuario = userRepository.findById(createCaronaRequest.usuarioId()).orElseThrow();
    Carro carro = carroRepository.findById(createCaronaRequest.carroId()).orElseThrow();
    Carona carona = Carona.builder().localOrigem(createCaronaRequest.localOrigem())
        .dataInicial(createCaronaRequest.dataInicial())
        .dataFinal(createCaronaRequest.dataFinal())
        .horarioSaida(createCaronaRequest.horarioSaida())
        .horarioRetorno(createCaronaRequest.horarioRetorno())
        .assentos(createCaronaRequest.numeroAssentos())
        .caronaRecorrente(createCaronaRequest.caronaRecorrente())
        .preco(createCaronaRequest.preco())
        .carro(carro)
        .cidadesPassagem(createCaronaRequest.cidadesPassagem())
        .usuario(usuario)
        .build();
    caronaRepository.save(carona);

    // conversaService.create(
    // new CreateConversaRequest("Carona de " + usuario.getFirstName(),
    // Arrays.asList(usuario.getId()), true));

    List<Usuario> usuarios = userRepository.findUsuariosPorEndereco(carona.getCidadesPassagem());

    usuarios.stream()
        .filter(user -> !user.getId().equals(usuario.getId()))
        .forEach(user -> expoPushNotificacaoService.sendPushNotification(
            user.getExpoNotificationToken(),
            "Novas caronas disponiveis",
            "Existe novas caronas disponveis para a sua cidade! Acesse o aplicativo da UniRotas e garanta a sua."));
  }

  public List<CaronasDisponiveisDTO> findAll() {
    List<Carona> caronasDisponiveis = caronaRepository.findAllCaronas();
    return caronasDisponiveis.stream().map(CaronasDisponiveisDTO::new).collect(Collectors.toList());
  }

  public CaronaDTO findById(Long id) {
    var carona = caronaRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Carona not found"));
    return new CaronaDTO(carona);
  }

  public List<CaronaByUserDTO> findAllByUserId(Long usuarioId) {
    return caronaRepository.findAllByUsuarioId(usuarioId).stream().map(CaronaByUserDTO::new)
        .collect(Collectors.toList());
  }

  public List<CaronasDisponiveisDTO> findAllByCity(String cidade) {
    return caronaRepository.findAllByCity(cidade).stream().map(CaronasDisponiveisDTO::new)
        .collect(Collectors.toList());
  }

  public void removerPassageiro(RemoverPassageiroRequest request) {
    Carona carona = caronaRepository.findById(request.caronaId())
        .orElseThrow(() -> new EntityNotFoundException("Carona not found"));
    Usuario usuario = userRepository.findById(request.usuarioId())
        .orElseThrow(() -> new EntityNotFoundException("Usuario not found"));

    if (carona.getPassageiros().remove(usuario)) {
      caronaRepository.save(carona);
    } else {
      throw new RuntimeException("Passageiro não existe na carona");
    }

    if (request.removido()) {
      expoPushNotificacaoService.sendPushNotification(usuario.getExpoNotificationToken(),
          "Removido", carona.getUsuario().getFirstName()
              + " removeu você da carona.");
    } else {
      expoPushNotificacaoService.sendPushNotification(carona.getUsuario().getExpoNotificationToken(),
          "Saiu da carona", usuario.getFirstName()
              + " decidiu sair da sua carona.");
    }

    List<Usuario> usuarios = userRepository.findUsuariosPorEndereco(carona.getCidadesPassagem());

    usuarios.stream()
        .filter(user -> !user.getId().equals(carona.getUsuario().getId())
            && !user.getId().equals(usuario.getId()))
        .forEach(user -> expoPushNotificacaoService.sendPushNotification(
            user.getExpoNotificationToken(),
            "Novas caronas disponiveis",
            "Existe novas caronas disponíveis para a sua cidade! Acesse o aplicativo da UniRotas e garanta a sua."));

  }

  public void finalizarCarona(Long caronaId) {
    Carona carona = caronaRepository.findById(caronaId)
        .orElseThrow(() -> new EntityNotFoundException("Carona not found"));

    carona.setFinalizado(true);

    caronaRepository.save(carona);

    carona.getPassageiros().stream()
        .filter(user -> !user.getId().equals(carona.getUsuario().getId()))
        .forEach(user -> expoPushNotificacaoService.sendPushNotification(
            user.getExpoNotificationToken(),
            "Carona Finalizada",
            "A carona de " + carona.getUsuario().getFirstName() + " chegou ao fim!"));
  }

}
