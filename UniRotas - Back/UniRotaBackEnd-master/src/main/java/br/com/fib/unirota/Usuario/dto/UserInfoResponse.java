package br.com.fib.unirota.Usuario.dto;

import java.util.List;

import br.com.fib.unirota.Carro.entity.Carro;
import br.com.fib.unirota.Usuario.entity.Role;

public record UserInfoResponse(Long id, String username, String firstName, String lastName, String avatar, String email,
        String curso, Role role,
        List<Carro> carros) {
}
