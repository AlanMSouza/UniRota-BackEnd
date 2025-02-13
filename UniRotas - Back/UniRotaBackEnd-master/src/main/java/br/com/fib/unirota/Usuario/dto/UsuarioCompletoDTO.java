package br.com.fib.unirota.Usuario.dto;

public record UsuarioCompletoDTO(Long id, String username, String firstName, String lastName, String avatar,
    String email, String curso,
    boolean receberNotificoesCaronaCidade, String cep, String rua, String bairro, String cidade, String estadoUF) {
}
