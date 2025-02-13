package br.com.fib.unirota.Usuario.service;

import org.springframework.stereotype.Service;

import br.com.fib.unirota.UploadImage.UploadImageService;
import br.com.fib.unirota.Usuario.dto.AtualizarUsuarioRequest;
import br.com.fib.unirota.Usuario.dto.UploadImagemPerfilRequest;
import br.com.fib.unirota.Usuario.dto.UsuarioCompletoDTO;
import br.com.fib.unirota.Usuario.entity.Usuario;
import br.com.fib.unirota.Usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {

  private final UsuarioRepository userRepository;
  private final UploadImageService uploadImagemPerfil;

  public UsuarioService(UsuarioRepository userRepository, UploadImageService uploadImagemPerfil) {
    this.userRepository = userRepository;
    this.uploadImagemPerfil = uploadImagemPerfil;
  }

  public UsuarioCompletoDTO findUserById(Long id) {
    UsuarioCompletoDTO usuario = userRepository.findUsuarioCompletoDTOById(id)
        .orElseThrow(() -> new EntityNotFoundException("Usuario não existe"));
    return usuario;
  }

  public String uploadImagemPerfil(Long usuarioId, UploadImagemPerfilRequest request) {
    var usuario = userRepository.findById(usuarioId).orElseThrow();
    String urlImage = uploadImagemPerfil.uploadImagemBase64(request.image());
    usuario.setAvatar(urlImage);
    userRepository.save(usuario);
    return urlImage;
  }

  public void atualizarUsuario(AtualizarUsuarioRequest atualizarUsuarioRequest) {
    Usuario usuario = userRepository.findById(atualizarUsuarioRequest.id())
        .orElseThrow(() -> new EntityNotFoundException("Usuario não existe!"));
    usuario.setFirstName(atualizarUsuarioRequest.firstName());
    usuario.setLastName(atualizarUsuarioRequest.lastName());
    usuario.setEmail(atualizarUsuarioRequest.email());
    usuario.setReceberNotificoesCaronaCidade(atualizarUsuarioRequest.receberNotificoesCaronaCidade());
    usuario.setCep(atualizarUsuarioRequest.cep());
    usuario.setRua(atualizarUsuarioRequest.rua());
    usuario.setBairro(atualizarUsuarioRequest.bairro());
    usuario.setCidade(atualizarUsuarioRequest.cidade());
    usuario.setEstadoUF(atualizarUsuarioRequest.estadoUF());
    userRepository.save(usuario);
  }
}
