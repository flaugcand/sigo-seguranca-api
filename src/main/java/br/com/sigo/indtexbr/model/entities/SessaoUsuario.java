package br.com.sigo.indtexbr.model.entities;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
@Data
@Entity
@Table(name = "sessao_usuario")
public class SessaoUsuario implements Serializable {

	private static final long serialVersionUID = -6286390893437399389L;

	@Id
	@Column(name = "chave_sessao")
	private String chaveSessao;

	@Column(name = "login")
	private String login;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_timeout")
	private Date timeout;

	public SessaoUsuario() {
		super();
	}

	public SessaoUsuario(String chave, String login) {
		this();
		this.chaveSessao = chave;
		this.login = login;
		Calendar timeOut = Calendar.getInstance();
		timeOut.add(Calendar.MINUTE, 15);
		this.timeout = timeOut.getTime();
	}

}
