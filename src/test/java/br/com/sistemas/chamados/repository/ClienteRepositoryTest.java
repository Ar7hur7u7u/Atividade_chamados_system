package br.com.sistemas.chamados.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import br.com.sistemas.chamados.entity.Cliente;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    @Test
    void deveSalvarCliente() {
        Cliente salvo = repository.save(new Cliente("Ana Souza", "ana@exemplo.com", "47999999999"));

        assertThat(salvo.getId()).isNotNull();
        assertThat(repository.findById(salvo.getId())).isPresent();
    }

    @Test
    void deveIndicarEmailExistente() {
        repository.save(new Cliente("Ana Souza", "ana@exemplo.com", "47999999999"));

        assertThat(repository.existsByEmail("ana@exemplo.com")).isTrue();
        assertThat(repository.existsByEmail("outro@exemplo.com")).isFalse();
    }

    @Test
    void deveIgnorarProprioIdAoVerificarEmail() {
        Cliente ana = repository.save(new Cliente("Ana Souza", "ana@exemplo.com", "47999999999"));
        Cliente joao = repository.save(new Cliente("João Silva", "joao@exemplo.com", "47988888888"));

        assertThat(repository.existsByEmailAndIdNot("ana@exemplo.com", ana.getId())).isFalse();
        assertThat(repository.existsByEmailAndIdNot("ana@exemplo.com", joao.getId())).isTrue();
    }

    @Test
    void deveRetornarVazioParaIdInexistente() {
        assertThat(repository.findById(99999L)).isEmpty();
    }
}
