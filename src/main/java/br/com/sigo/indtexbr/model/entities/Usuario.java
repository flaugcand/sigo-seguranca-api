package br.com.sigo.indtexbr.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 798952926884465644L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;

	@Column(name = "login_usuario", unique = true, nullable = false)
	private String login;

	@Column(name = "sen_usuario", nullable = false)
	private String senha;

	@Column(name = "nom_usuario", nullable = false)
	private String nome;
	
	@Column(name = "sit_ativo", columnDefinition = "boolean default true")
	protected Boolean ativo;

}
