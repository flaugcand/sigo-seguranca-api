package br.com.sigo.indtexbr.service;

import static br.com.sigo.indtexbr.utils.StringUtil.isValid;
import static br.com.sigo.indtexbr.utils.StringUtil.toUpper;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigo.indtexbr.exceptions.GenericException;
import br.com.sigo.indtexbr.model.entities.Usuario;
import br.com.sigo.indtexbr.repository.UsuarioRepostiory;

@Service
public class UsuarioService {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioRepostiory repository;

	public Usuario salvar(final Usuario usuario) throws Exception {
		try {
			if (isValid(usuario.getNome()))
				usuario.setNome(toUpper(usuario.getNome()));

			return repository.save(usuario);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	public List<Usuario> findAll() throws Exception {
		try {

			return repository.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());

			throw e;
		}
	}

	public void delete(Long id) throws Exception {
		try {

			repository.deleteById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	public Usuario findById(Long id) throws Exception {
		try {
			Optional<Usuario> retorno = repository.findById(id);
			if (retorno.isPresent())
				return retorno.get();

			throw new GenericException("Registro não Encontrado");
		} catch (Exception e) {
			logger.error(e.getMessage());

			throw e;
		}
	}

	public Optional<Usuario> findByLogin(String login) {
		return repository.findByLogin(login);
	}

	public Usuario autenticar(String login, String senha) {
		return repository.autenticar(login, senha);
	}

}
