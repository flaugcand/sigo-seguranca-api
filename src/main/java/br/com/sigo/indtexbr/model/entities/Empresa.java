package br.com.sigo.indtexbr.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "empresa")
@NoArgsConstructor
public class Empresa implements Serializable {

	private static final long serialVersionUID = -827446683064150722L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_empresa")
	private Long id;

	@Column(name = "nome_fantasia", nullable = false)
	private String nomeFantasia;

	@Column(name = "razao_social", nullable = false)
	private String razaoSocial;

	@Column(name = "cnpj", nullable = false, unique = true)
	private String cnpj;

	@Column(name = "insc_estadual", nullable = true, unique = true)
	private String inscricaoEstadual;
	
	@Column(name = "tx_observacao")
	private String observacao;
	
	@Column(name="ds_email")
	private String email;

}
