package br.com.fib.unirota.Solicitacao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fib.unirota.Solicitacao.dto.SolicitacaoRequest;
import br.com.fib.unirota.Solicitacao.service.SolicitacaoService;

@RestController
@RequestMapping("/solicitacao")
public class SolicitacaoController {

  private final SolicitacaoService solicitacaoService;

  public SolicitacaoController(SolicitacaoService solicitacaoService) {
    this.solicitacaoService = solicitacaoService;
  }

  @PostMapping
  public void novaSolicitacao(@RequestBody SolicitacaoRequest solicitacaoRequest) {
    solicitacaoService.novaSolicitacao(solicitacaoRequest);
  }

  @GetMapping("/{caronaId}/solicitacoesPendentes")
  public ResponseEntity<?> listAllSolicitacoesPendentes(@PathVariable Long caronaId) {
    return ResponseEntity.ok().body(solicitacaoService.solicitacoesPendentes(caronaId));
  }

  @PostMapping("/{solicitacaoId}/aceitarSolicitacao")
  public ResponseEntity<?> aceitarSolicitacao(@PathVariable Long solicitacaoId) {
    solicitacaoService.aceitarSolicitacao(solicitacaoId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/{solicitacaoId}/recusarSolicitacao")
  public ResponseEntity<?> recusarSolicitacao(@PathVariable Long solicitacaoId) {
    solicitacaoService.recusarSolicitacao(solicitacaoId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
