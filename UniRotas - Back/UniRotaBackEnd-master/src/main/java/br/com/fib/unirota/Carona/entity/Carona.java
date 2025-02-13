package br.com.fib.unirota.Carona.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.fib.unirota.Carro.entity.Carro;
import br.com.fib.unirota.Solicitacao.entity.Solicitacao;
import br.com.fib.unirota.Usuario.entity.Usuario;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Carona {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "local_origem")
  private String localOrigem;

  private BigDecimal preco;

  @Column(name = "data_inicial")
  private LocalDate dataInicial;

  @Column(name = "data_final")
  private LocalDate dataFinal;

  @Column(name = "horario_saida")
  private String horarioSaida;

  @Column(name = "horario_retorno")
  private String horarioRetorno;

  private int assentos;

  @ManyToOne
  private Usuario usuario;

  @Column(name = "carona_recorrente")
  private boolean caronaRecorrente;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private boolean finalizado;

  @ManyToOne
  private Carro carro;

  @ManyToMany
  @JoinTable(name = "carona_passageiros", joinColumns = @JoinColumn(name = "carona_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
  private List<Usuario> passageiros;

  @OneToMany(mappedBy = "carona")
  @JsonIgnore
  private List<Solicitacao> solicitoes;

  @ElementCollection
  @CollectionTable(name = "cidades_passagem", joinColumns = @JoinColumn(name = "carona_id"))
  @Column(name = "cidade")
  private List<String> cidadesPassagem;

  // @OneToOne(cascade = CascadeType.ALL)
  // private Conversa conversa;
}
