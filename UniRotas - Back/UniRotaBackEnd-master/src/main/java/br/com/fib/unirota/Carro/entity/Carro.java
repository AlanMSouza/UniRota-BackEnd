package br.com.fib.unirota.Carro.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.fib.unirota.Carona.entity.Carona;
import br.com.fib.unirota.Usuario.entity.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carro {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String placa;

  private String cor;

  private String modelo;

  @ManyToOne
  @JsonIgnore
  private Usuario usuario;

  @OneToMany(mappedBy = "carro", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Carona> caronas;
}
