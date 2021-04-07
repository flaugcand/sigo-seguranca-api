package br.com.sigo.indtexbr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sigo.indtexbr.model.entities.SessaoUsuario;

public interface SessaoUsuarioRepository extends JpaRepository<SessaoUsuario, String> {

}
