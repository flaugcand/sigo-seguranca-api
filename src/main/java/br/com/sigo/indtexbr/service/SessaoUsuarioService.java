package br.com.sigo.indtexbr.service;

import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigo.indtexbr.exceptions.GenericException;
import br.com.sigo.indtexbr.model.entities.SessaoUsuario;
import br.com.sigo.indtexbr.model.entities.Usuario;
import br.com.sigo.indtexbr.repository.SessaoUsuarioRepository;
import br.com.sigo.indtexbr.utils.TokenUtil;

@Service
public class SessaoUsuarioService {

	@Autowired
	private SessaoUsuarioRepository repository;

	@Autowired
	private UsuarioService usuarioService;

	public String autenticar(String token) throws GenericException {
		Optional<SessaoUsuario> sessao = repository.findById(token);
		if (sessao.isPresent())
			return sessao.get().getChaveSessao();
		else {
			String aux = decode(token);
			if (aux.contains(":")) {
				String dados[] = aux.split(":");
				Usuario usuario = usuarioService.autenticar(dados[0], dados[1]);
				if (usuario != null)
					return encode(usuario);
			}
		}

		throw new GenericException("Falha na autenticação");
	}

	private String decode(String token) {
		return new String(Base64.decodeBase64(token.replace("Authorization: 'Basic '", "")));
	}

	private String encode(Usuario usuario) {
		String chave = TokenUtil.generateRandomStringByUUID();
		repository.save(new SessaoUsuario(chave, usuario.getLogin()));
		return chave;
	}

	public SessaoUsuario buscarSessaoPorToken(String token) throws GenericException {
		Optional<SessaoUsuario> sessao = repository.findById(token);
		if (sessao.isPresent())
			return sessao.get();

		throw new GenericException("Sessão inválida");
	}

}
