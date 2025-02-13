package br.com.fib.unirota.Usuario.dto;

public record AtualizarUsuarioRequest(Long id, String firstName, String lastName, String email,
    boolean receberNotificoesCaronaCidade, String cep, String rua, String bairro, String cidade, String estadoUF) {

}
