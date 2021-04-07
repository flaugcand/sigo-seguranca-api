package br.com.sigo.indtexbr.service;

import static br.com.sigo.indtexbr.utils.StringUtil.getMensagemCampoObrigatorio;
import static br.com.sigo.indtexbr.utils.StringUtil.isValid;
import static br.com.sigo.indtexbr.utils.StringUtil.toUpper;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sigo.indtexbr.exceptions.GenericException;
import br.com.sigo.indtexbr.model.entities.Empresa;
import br.com.sigo.indtexbr.repository.EmpresaRepository;;

@Service
public class EmpresaService {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(EmpresaService.class);

	@Autowired
	private EmpresaRepository repository;

	public Empresa salvar(final Empresa empresa) throws Exception {
		try {
			if (!isValid(empresa.getNomeFantasia()))
				throw new GenericException(getMensagemCampoObrigatorio("Nome Fantasia"));

			if (!isValid(empresa.getRazaoSocial()))
				throw new GenericException(getMensagemCampoObrigatorio("Razão Social"));

			if (!isValid(empresa.getCnpj()))
				throw new GenericException(getMensagemCampoObrigatorio("CNPJ"));

			empresa.setNomeFantasia(toUpper(empresa.getNomeFantasia()));
			empresa.setRazaoSocial(toUpper(empresa.getRazaoSocial()));

			return repository.save(empresa);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	public List<Empresa> findAll() throws Exception {
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

	public Empresa findById(Long id) throws Exception {
		try {
			Optional<Empresa> retorno = repository.findById(id);
			if (retorno.isPresent())
				return retorno.get();

			throw new GenericException("Registro não Encontrado");
		} catch (Exception e) {
			logger.error(e.getMessage());

			throw e;
		}
	}

}