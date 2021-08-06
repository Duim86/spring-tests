package com.testes.service;

import com.testes.excpetion.NegocioException;
import com.testes.excpetion.PessoaAtivaException;
import com.testes.model.Pessoa;
import com.testes.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

  private Long pessoaId;
  private Pessoa pessoa;

  @InjectMocks
  private PessoaService pessoaService;

  @Mock
  private PessoaRepository pessoaRepository;

  @BeforeEach
  void setup() {
    pessoa = new Pessoa(1L, "Alexandre Zanlorenzi", "04723645969");
    pessoaId = 1L;
  }


  @Test
  void deveBuscarUmaPessoaQuandoIdValido() {

    when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));

    var pessoaDoBanco = pessoaService.buscarOuFalhar(pessoaId);

    assertThat(pessoa).isEqualTo(pessoaDoBanco);
  }

  @Test
  void criarPessoa() {

    when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

    var pessoaSalva = pessoaService.salvar(pessoa);
    assertTrue(pessoaSalva.getAtivo());
  }

  @Test
  void quandoCpfRepetidoEntaoInformarCpfJaRegistrado() throws DataIntegrityViolationException {


    when(pessoaRepository.save(pessoa)).thenThrow(new DataIntegrityViolationException(""));

    assertThrows(NegocioException.class, () -> pessoaService.salvar(pessoa));
  }

  @Test
  void deveRemoverQuandoPessoaInativa() {
    pessoa.setAtivo(false);

    when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));

    pessoaService.remover(pessoaId);

    verify(pessoaRepository, times(1)).findById(pessoaId);
    verify(pessoaRepository, times(1)).deleteById(pessoaId);
  }

  @Test
  void deveDesativarUmUsuarioQuandoPessoaAtiva() {

    when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));

    pessoaService.desativar(pessoaId);

    assertFalse(pessoa.getAtivo());
  }

  @Test
  void deveAtivarUmUsuarioQuandoPessoaInativa() {
    pessoa.setAtivo(false);

    when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));

    pessoaService.ativar(pessoaId);

    assertTrue(pessoa.getAtivo());
  }

  @Test
  void deveFalharQuandoAtivarPessoaAtiva() {

    when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));

    assertThrows(PessoaAtivaException.class, () -> pessoaService.ativar(pessoaId));
  }

  @Test
  void deveFalharAoRemoverQuandoPessoaAtiva() {

    when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));

    assertThrows(PessoaAtivaException.class, () -> pessoaService.remover(pessoaId));
  }
}
