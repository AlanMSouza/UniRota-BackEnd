package br.com.fib.unirota.Usuario.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.fib.unirota.Carona.entity.Carona;
import br.com.fib.unirota.Carro.entity.Carro;
import br.com.fib.unirota.Conversa.entity.Conversa;
import br.com.fib.unirota.Mensagem.entity.Mensagem;
import br.com.fib.unirota.Notificacao.entity.Notificacao;
import br.com.fib.unirota.Solicitacao.entity.Solicitacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(unique = true)
  private String username;

  @JsonIgnore
  private String password;

  private String firstName;

  private String lastName;

  private String avatar;

  private String email;

  private String curso;

  @Column(nullable = false, columnDefinition = "boolean default true")
  private boolean receberNotificoesCaronaCidade;

  private String cep;

  private String rua;

  private String bairro;

  private String cidade;

  private String estadoUF;

  @Column(name = "expo_notification_token")
  private String expoNotificationToken;

  @JsonIgnore
  @Enumerated(EnumType.STRING)
  private Role role;

  @JsonIgnore
  @OneToMany(mappedBy = "usuario")
  private List<Carro> carros;

  @JsonIgnore
  @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
  private List<Carona> caronasCriada;

  @JsonIgnore
  @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
  private List<Solicitacao> solicitacoes;

  @JsonIgnore
  @OneToMany(mappedBy = "destinatario", fetch = FetchType.LAZY)
  private List<Notificacao> notificacoes;

  @JsonIgnore
  @ManyToMany(mappedBy = "passageiros", fetch = FetchType.LAZY)
  private List<Carona> caronasPassageiro;

  @JsonIgnore
  @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
  private List<Conversa> conversas;

  @JsonIgnore
  @OneToMany(mappedBy = "usuarioRemetente", fetch = FetchType.LAZY)
  private List<Mensagem> mensagens;

  @JsonIgnore
  @ManyToMany(mappedBy = "listaVisualizados", fetch = FetchType.LAZY)
  private List<Mensagem> mensagensVisualizadas;
}
