package br.com.fib.unirota.Authentication.dto;

import br.com.fib.unirota.Usuario.dto.UserInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private String token;
  private UserInfoResponse userInfo;
}
