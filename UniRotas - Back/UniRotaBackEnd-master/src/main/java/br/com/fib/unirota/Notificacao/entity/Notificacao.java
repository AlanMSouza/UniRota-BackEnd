package br.com.fib.unirota.Notificacao.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notificacao {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_notificacao")
  private TipoNotificacao TipoNotificacao;

  @ManyToOne
  private Usuario remetente;

  @ManyToOne
  private Usuario destinatario;

  private boolean visualizado;

  @CreationTimestamp
  private LocalDateTime dataCriacao;
}
