package br.com.fib.unirota.Solicitacao.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.fib.unirota.Carona.entity.Carona;
import br.com.fib.unirota.Usuario.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Solicitacao {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne
  @JsonIgnore
  private Usuario usuario;

  @Column(name = "status_solicitacao")
  @Enumerated(EnumType.STRING)
  private StatusSolicitacao statusSolicitacao;

  @CreationTimestamp
  @Column(name = "data_criacao")
  private LocalDateTime dataCriacao;

  @ManyToOne
  @JsonIgnore
  private Carona carona;
}
