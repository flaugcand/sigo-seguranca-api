package br.com.sigo.indtexbr.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sigo.indtexbr.model.dto.CredenciaisDeUsuarioDto;
import br.com.sigo.indtexbr.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@Api("REST API de Autenticação.")
@RestController
@RequestMapping("${app.api.base}/autenticacao")
public class AutenticacaoRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@ResponseBody
	@ApiOperation(value = "Gerar o token para aplicação. Necessários usuario, senha.")
	@PostMapping("/gettoken")
	public ResponseEntity<String> gerarToken(@RequestBody CredenciaisDeUsuarioDto credentials) {
		log.info("Inicio da geracao do token.");

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), Collections.emptyList()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = tokenService.addAuthentication(authentication);

		log.info("Token gerado com sucesso: {}", token);
		return ResponseEntity.ok(token);
	}
}
