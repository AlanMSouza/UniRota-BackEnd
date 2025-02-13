package br.com.fib.unirota.Usuario.dto;

import br.com.fib.unirota.Usuario.entity.Usuario;

public record UsuarioDTO(Long id, String firstName, String lastName, String avatar, String curso) {

  public UsuarioDTO(Usuario usuario) {
    this(usuario.getId(), usuario.getFirstName(), usuario.getLastName(), usuario.getAvatar(), usuario.getCurso());
  }
}
