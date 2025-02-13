package br.com.fib.unirota.Authentication.Controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.fib.unirota.Authentication.Service.AuthenticationService;
import br.com.fib.unirota.Authentication.dto.AuthenticationResponse;
import br.com.fib.unirota.Authentication.dto.PushExpoToken;
import br.com.fib.unirota.Authentication.dto.RegisterRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    authenticationService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody PushExpoToken expoToken,
      Authentication authentication) {
    return ResponseEntity.ok(authenticationService.authenticate(expoToken, authentication));
  }

}
