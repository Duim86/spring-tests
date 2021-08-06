package com.testes.excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException extends RuntimeException{
  public static final long serialVersionUID = 1L;

  public EntidadeNaoEncontradaException(String mensagem) {
    super(mensagem);
  }

  public EntidadeNaoEncontradaException(Long entidadeId) {
    this("A entidade de id " + entidadeId + " não foi encontrada");
  }
}
