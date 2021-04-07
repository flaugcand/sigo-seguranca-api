package br.com.sigo.indtexbr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.sigo.indtexbr.model.dto.CustomUserDetails;
import br.com.sigo.indtexbr.model.entities.Usuario;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Long code = null;
		String password = "";

		List<GrantedAuthority> listAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");

		Optional<Usuario> opt = usuarioService.findByLogin(userName);
		if (opt.isPresent()) {
			code = opt.get().getId();
			password = opt.get().getSenha();
		}

		return new CustomUserDetails(userName, password, code, listAdmin);
	}

}