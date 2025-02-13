package br.com.fib.unirota.Conversa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fib.unirota.Conversa.dto.CreateConversaRequest;
import br.com.fib.unirota.Conversa.dto.VisualizouMensagemRequest;
import br.com.fib.unirota.Conversa.service.ConversaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/conversa")
public class ConversaController {

  private final ConversaService conversaService;

  public ConversaController(ConversaService conversaService) {
    this.conversaService = conversaService;
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody CreateConversaRequest createConversaRequest) {

    return ResponseEntity.status(HttpStatus.CREATED).body(conversaService.create(createConversaRequest));
  }

  @GetMapping("/listAll/{id}")
  public ResponseEntity<?> getAll(@PathVariable Long id) {
    return ResponseEntity.ok(conversaService.findAllByUsuarioId(id));
  }

  @PostMapping("/{id}/visualizou")
  public ResponseEntity<?> visualizar(@PathVariable Long id,
      @RequestBody VisualizouMensagemRequest visualizouMensagemRequest) {
    conversaService.visualizarMensagem(id, visualizouMensagemRequest);
    return ResponseEntity.ok().body(null);
  }

}
