package com.testes.service;

import com.testes.excpetion.NegocioException;
import com.testes.excpetion.PessoaAtivaException;
import com.testes.model.Pessoa;
import com.testes.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PessoaService {

  @Autowired
  private PessoaRepository pessoaRepository;

  @Transactional
  public Pessoa salvar(Pessoa pessoa) {
    try {
      return pessoaRepository.save(pessoa);
    } catch (DataIntegrityViolationException e) {
      throw new NegocioException("O cpf já está registrado");
    }
  }

  @Transactional
  public void remover(Long pessoaId) {
    var pessoa = buscarOuFalhar(pessoaId);

    if(Boolean.TRUE.equals(pessoa.getAtivo())) {
      throw new PessoaAtivaException(pessoaId);
    }

    pessoaRepository.deleteById(pessoaId);

  }

  @Transactional
  public void desativar(long pessoaId) {
    var pessoa = buscarOuFalhar(pessoaId);
    pessoa.desativar();
  }

  @Transactional
  public void ativar(long pessoaId) {
    var pessoa = buscarOuFalhar(pessoaId);

    if(Boolean.TRUE.equals(pessoa.getAtivo())) {
      throw new PessoaAtivaException(pessoaId);
    }
    pessoa.ativar();
  }

  public Pessoa buscarOuFalhar(Long pessoaId) {
    return pessoaRepository.findById(pessoaId)
            .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));
  }
}
