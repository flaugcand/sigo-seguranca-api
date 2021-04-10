package br.com.sigo.indtexbr.controllers;

import static br.com.sigo.indtexbr.utils.Constante.APPLICATION_JSON;
import static br.com.sigo.indtexbr.utils.Constante.HTTP_SUCESS;
import static br.com.sigo.indtexbr.utils.Constante.SUCCESS;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sigo.indtexbr.model.entities.Empresa;
import br.com.sigo.indtexbr.service.EmpresaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api("REST API de cadastro de Empresas")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("${app.api.base}/empresa")
public class EmpresaRestController {

	@Autowired
	private EmpresaService service;

	@ApiResponses({ @ApiResponse(code = HTTP_SUCESS, message = "Success") })
	@ApiOperation(value = "Método de criação de Empresa")
	@GetMapping(produces = APPLICATION_JSON)
	public @ResponseBody ResponseEntity<List<Empresa>> consultar(
			@RequestHeader("Authorization") Map<String, String> authorization,
			@RequestParam(name = "nomeFantasia", required = false) String nomeFantasia,
			@RequestParam(name = "razaoSocial", required = false) String razaoSocial,
			@RequestParam(name = "cnpj", required = false) String cnpj,
			@RequestParam(name = "inscricaoEstadual", required = false) String inscricaoEstadual) {
		try {
			List<Empresa> findAll = service.findByParameters(nomeFantasia, razaoSocial, cnpj, inscricaoEstadual);

			return ResponseEntity.ok(findAll);
		} catch (Exception e) {
			log.error("Erro ao salvar a empresa", e);
			return ResponseEntity.badRequest().build();
		}

	}

	@ApiResponses({ @ApiResponse(code = HTTP_SUCESS, message = "Success") })
	@ApiOperation(value = "Método de criação de Empresa")
	@PostMapping(produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
	public @ResponseBody ResponseEntity<Empresa> salvaEmpresa(
			@RequestHeader("Authorization") Map<String, String> authorization, @RequestBody Empresa empresa) {
		try {
			empresa.setId(null);
			Empresa entidade = service.salvar(empresa);

			return ResponseEntity.ok(entidade);
		} catch (Exception e) {
			log.error("Erro ao salvar a empresa", e);
			return ResponseEntity.badRequest().build();
		}

	}

	@ApiResponses({ @ApiResponse(code = HTTP_SUCESS, message = SUCCESS, response = Empresa.class) })
	@ApiOperation(value = "Método de atualização da Empresa")
	@PutMapping(produces = APPLICATION_JSON, consumes = APPLICATION_JSON, value = "/{id}")
	public @ResponseBody ResponseEntity<Empresa> atualizaEmpresa(
			@RequestHeader("Authorization") Map<String, String> authorization, @PathVariable(name = "id") Long id,
			@RequestBody Empresa empresa) throws Exception {
		try {
			Empresa entidade = service.salvar(empresa);

			return ResponseEntity.ok(entidade);
		} catch (Exception e) {
			log.error("Erro ao atualizar a empresa", e);
			return ResponseEntity.badRequest().build();
		}
	}

	@ApiResponses({ @ApiResponse(code = HTTP_SUCESS, message = SUCCESS) })
	@ApiOperation(value = "Método de atualização da Empresa")
	@DeleteMapping(produces = APPLICATION_JSON, value = "/{id}")
	public @ResponseBody ResponseEntity<Empresa> excluiEmpresa(
			@RequestHeader("Authorization") Map<String, String> authorization, @PathVariable(value = "id") Long id)
			throws Exception {
		try {
			service.delete(id);

			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			log.error("Erro ao atualizar a empresa", e);
			return ResponseEntity.badRequest().build();
		}
	}

}
