package br.com.fib.unirota.Authentication.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fib.unirota.Authentication.dto.AuthenticationResponse;
import br.com.fib.unirota.Authentication.dto.PushExpoToken;
import br.com.fib.unirota.Authentication.dto.RegisterRequest;
import br.com.fib.unirota.Usuario.dto.UserInfoResponse;
import br.com.fib.unirota.Usuario.entity.Role;
import br.com.fib.unirota.Usuario.entity.Usuario;
import br.com.fib.unirota.Usuario.repository.UsuarioRepository;

@Service
public class AuthenticationService {

  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final UsuarioRepository userRepository;

  public AuthenticationService(JwtService jwtService, UsuarioRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  public AuthenticationResponse authenticate(PushExpoToken expoToken, Authentication authentication) {
    var usuario = userRepository.findByUsername(authentication.getName());
    usuario.get().setExpoNotificationToken(expoToken.expoToken());
    userRepository.save(usuario.get());

    return AuthenticationResponse.builder()
        .token(jwtService.generateToken(authentication))
        .userInfo(usuario
            .map(user -> new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getAvatar(),
                user.getEmail(),
                user.getCurso(),
                user.getRole(),
                user.getCarros()))
            .orElseThrow())
        .build();
  }

  public void register(RegisterRequest request) {
    Usuario usuario = Usuario.builder()
        .username(request.username())
        .password(passwordEncoder.encode(request.password()))
        .firstName(request.firstName())
        .lastName(request.lastName())
        .email(request.email())
        .curso(request.curso())
        .receberNotificoesCaronaCidade(request.receberNotificoesCaronaCidade())
        .cep(request.cep())
        .rua(request.rua())
        .bairro(request.bairro())
        .cidade(request.cidade())
        .estadoUF(request.estadoUF())
        .role(Role.ADMIN)
        .build();

    userRepository.save(usuario);
  }
}
