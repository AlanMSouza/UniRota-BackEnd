package br.com.fib.unirota.Usuario.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fib.unirota.Usuario.entity.UserAuthenticated;
import br.com.fib.unirota.Usuario.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UsuarioRepository userRepository;

  public UserDetailsServiceImpl(UsuarioRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .map(UserAuthenticated::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

}
