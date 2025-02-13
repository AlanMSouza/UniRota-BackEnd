package br.com.fib.unirota.Authentication.dto;

public record RegisterRequest(
                String username,
                String password,
                String firstName,
                String lastName,
                String email,
                String curso,
                boolean receberNotificoesCaronaCidade,
                String cep,
                String rua,
                String bairro,
                String cidade,
                String estadoUF) {
}