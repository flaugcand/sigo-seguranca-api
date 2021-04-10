package br.com.sigo.indtexbr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.sigo.indtexbr.model.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	@Query("SELECT e FROM br.com.sigo.indtexbr.model.entities.Empresa e WHERE 1=1"
			+ " AND (e.nomeFantasia LIKE :nomeFantasia OR :nomeFantasia IS NULL)"
			+ " AND (e.razaoSocial LIKE :razaoSocial OR :razaoSocial IS NULL)"
			+ " AND (e.cnpj LIKE :cnpj OR :cnpj IS NULL)"
			+ " AND (e.inscricaoEstadual LIKE :inscricaoEstadual OR :inscricaoEstadual IS NULL)"
			+ " ORDER BY e.nomeFantasia asc"
			)
	List<Empresa> findByParameters(String nomeFantasia, String razaoSocial,
			String cnpj, String inscricaoEstadual);

}
