package br.com.fib.unirota.Mensagem.dto;

public record EnviarMensagemRequest(Long conversaId, String corpo, Long usuarioRemetente) {

}
