package br.com.fib.unirota.Mensagem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fib.unirota.Mensagem.dto.EnviarMensagemRequest;
import br.com.fib.unirota.Mensagem.service.MensagemService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/mensagem")
public class MensagemController {

  private final MensagemService mensagemService;

  public MensagemController(MensagemService mensagemService) {
    this.mensagemService = mensagemService;
  }

  @PostMapping
  public ResponseEntity<?> enviarMensagem(@RequestBody EnviarMensagemRequest enviarMensagemRequest) {
    mensagemService.enviarMensagem(enviarMensagemRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }

  @GetMapping("/conversa/{id}")
  public ResponseEntity<?> buscarMensagens(@PathVariable Long id) {
    return ResponseEntity.ok(mensagemService.buscarMensagensByConversaId(id));
  }

}
