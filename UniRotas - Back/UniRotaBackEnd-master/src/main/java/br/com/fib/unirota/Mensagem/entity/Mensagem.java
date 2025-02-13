package br.com.fib.unirota.Mensagem.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import br.com.fib.unirota.Conversa.entity.Conversa;
import br.com.fib.unirota.Usuario.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Mensagem {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String corpo;

  private String imagem;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "mensagem_visualizou", joinColumns = @JoinColumn(name = "mensagem_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
  private List<Usuario> listaVisualizados;

  @ManyToOne(fetch = FetchType.LAZY)
  private Conversa conversa;

  @ManyToOne(fetch = FetchType.LAZY)
  private Usuario usuarioRemetente;

  @CreationTimestamp
  @Column(name = "data_criacao")
  private LocalDateTime dataCriacao;
}
