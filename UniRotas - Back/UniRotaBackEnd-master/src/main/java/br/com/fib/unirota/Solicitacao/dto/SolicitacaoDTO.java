package br.com.fib.unirota.Solicitacao.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.fib.unirota.Solicitacao.entity.Solicitacao;
import br.com.fib.unirota.Solicitacao.entity.StatusSolicitacao;
import br.com.fib.unirota.Usuario.dto.UsuarioDTO;

public record SolicitacaoDTO(Long id, UsuarioDTO usuario, StatusSolicitacao statusSolicitacao,
        LocalDateTime dataCriacao, Long caronaId, BigDecimal valorCarona) {
    public SolicitacaoDTO(Solicitacao solicitacao) {
        this(
                solicitacao.getId(),
                new UsuarioDTO(solicitacao.getUsuario()),
                solicitacao.getStatusSolicitacao(),
                solicitacao.getDataCriacao(),
                solicitacao.getCarona().getId(),
                solicitacao.getCarona().getPreco());
    }
}
