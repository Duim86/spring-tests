package com.testes.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NonNull
  private Long id;

  @NonNull
  private String nome;

  private Boolean ativo = true;

  @NonNull
  private String cpf;

  public void desativar () {
    setAtivo(false);
  }

  public void ativar() {
    setAtivo(true);
  }
}
