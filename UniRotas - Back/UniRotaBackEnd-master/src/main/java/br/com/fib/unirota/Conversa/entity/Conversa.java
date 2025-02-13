package br.com.fib.unirota.Conversa.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import br.com.fib.unirota.Mensagem.entity.Mensagem;
import br.com.fib.unirota.Usuario.entity.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Conversa {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String nome;

  private boolean isGroup;

  @Column(name = "data_ultima_mensagem")
  private LocalDateTime dataUltimaMensagem;

  @CreationTimestamp
  @Column(name = "data_criacao")
  private LocalDateTime dataCriacao;

  @OneToMany(mappedBy = "conversa", fetch = FetchType.LAZY)
  private List<Mensagem> mensagens;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "conversa_usuarios", joinColumns = @JoinColumn(name = "conversa_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
  private List<Usuario> usuarios;

  // @OneToOne
  // private Carona carona;

}
