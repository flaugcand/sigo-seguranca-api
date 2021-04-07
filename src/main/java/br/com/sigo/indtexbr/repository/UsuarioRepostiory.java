package br.com.sigo.indtexbr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sigo.indtexbr.model.entities.Usuario;

public interface UsuarioRepostiory extends JpaRepository<Usuario, Long> {

	@Query(value = "SELECT entidade FROM Usuario entidade WHERE entidade.ativo = true AND entidade.login = :login AND entidade.senha = :senha")
	Usuario autenticar(@Param("login") String login, @Param("senha") String senha);

	Optional<Usuario> findByLogin(String nome);
}
