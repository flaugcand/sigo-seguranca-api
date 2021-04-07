package br.com.sigo.indtexbr.controllers;

import static br.com.sigo.indtexbr.utils.Constante.HTTP_SUCESS;
import static br.com.sigo.indtexbr.utils.Constante.SUCCESS;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sigo.indtexbr.exceptions.GenericException;
import br.com.sigo.indtexbr.model.dto.CredenciaisDeUsuarioDto;
import br.com.sigo.indtexbr.model.dto.UsuarioAutenticadoDTO;
import br.com.sigo.indtexbr.service.SessaoUsuarioService;
import br.com.sigo.indtexbr.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@Api("REST API de Autenticação.")
@RestController
@RequestMapping("${app.api.base}/autenticacao")
public class AutenticacaoRestController {

	@Autowired
	private SessaoUsuarioService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@ApiResponses({@ApiResponse(code = HTTP_SUCESS, message = SUCCESS, response = UsuarioAutenticadoDTO.class)})
	@ApiOperation(value = "Método de autenticação de usuários.")
	@GetMapping(produces = "application/json")
	@RequestMapping({"/validateLogin"})
	public ResponseEntity<UsuarioAutenticadoDTO> validateLogin(@RequestHeader("token") String token) {
		try {
			System.out.println(token);
			service.autenticar(token);
			return ResponseEntity.ok(new UsuarioAutenticadoDTO());
		} catch (GenericException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.badRequest().build();
	}

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
