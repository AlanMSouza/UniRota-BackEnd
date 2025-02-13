package br.com.fib.unirota.Usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fib.unirota.Usuario.dto.UsuarioCompletoDTO;
import br.com.fib.unirota.Usuario.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByUsername(String username);

  @Query("""
          SELECT new br.com.fib.unirota.Usuario.dto.UsuarioCompletoDTO(
              u.id,
              u.username,
              u.firstName,
              u.lastName,
              u.avatar,
              u.email,
              u.curso,
              u.receberNotificoesCaronaCidade,
              u.cep,
              u.rua,
              u.bairro,
              u.cidade,
              u.estadoUF
          )
          From Usuario u
          WHERE u.id = :id
      """)
  Optional<UsuarioCompletoDTO> findUsuarioCompletoDTOById(Long id);

  @Query("""
          SELECT u
          From Usuario u
          WHERE u.cidade IN (:cidades)
      """)
  List<Usuario> findUsuariosPorEndereco(List<String> cidades);
}
