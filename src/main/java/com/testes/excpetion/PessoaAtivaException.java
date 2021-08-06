package com.testes.excpetion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PessoaAtivaException extends RuntimeException{
  public static final long serialVersionUID = 1L;

  public PessoaAtivaException(String mensagem) {
    super(mensagem);
  }

  public PessoaAtivaException(Long pessoaId) {
    this("A pessoa de id " + pessoaId + " está ativa");
  }
}
