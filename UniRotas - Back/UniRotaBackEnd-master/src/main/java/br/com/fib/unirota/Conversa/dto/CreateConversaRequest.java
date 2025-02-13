package br.com.fib.unirota.Conversa.dto;

import java.util.List;

public record CreateConversaRequest(String nome, List<Long> usuarioIds, boolean isGroup) {
}
