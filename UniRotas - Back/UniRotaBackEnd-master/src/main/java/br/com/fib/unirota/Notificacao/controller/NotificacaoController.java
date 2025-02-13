package br.com.fib.unirota.Notificacao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fib.unirota.Notificacao.service.NotificacaoService;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

  private final NotificacaoService notificacaoService;

  public NotificacaoController(NotificacaoService notificacaoService) {
    this.notificacaoService = notificacaoService;
  }

  @GetMapping
  public ResponseEntity<?> listAll() {
    return ResponseEntity.ok().body(notificacaoService.findAll());
  }

}
