package br.com.fib.unirota.Usuario.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fib.unirota.Usuario.dto.AtualizarUsuarioRequest;
import br.com.fib.unirota.Usuario.dto.UploadImagemPerfilRequest;
import br.com.fib.unirota.Usuario.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  private final UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @GetMapping("/infoCompleta/{id}")
  public ResponseEntity<?> findUsuarioById(@PathVariable Long id) {
    return ResponseEntity.ok(usuarioService.findUserById(id));
  }

  @PostMapping("/uploadImagemPerfil/{usuarioId}")
  public ResponseEntity<?> uploadImagemPerfil(@PathVariable Long usuarioId,
      @RequestBody UploadImagemPerfilRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.uploadImagemPerfil(usuarioId, request));
  }

  @PutMapping
  public ResponseEntity<?> atualizarUsuario(@RequestBody AtualizarUsuarioRequest atualizarUsuarioRequest) {
    usuarioService.atualizarUsuario(atualizarUsuarioRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
