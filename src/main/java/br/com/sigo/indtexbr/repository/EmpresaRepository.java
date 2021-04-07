package br.com.sigo.indtexbr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sigo.indtexbr.model.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
