package br.com.sigo.indtexbr.model.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class MailDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7400385898422425389L;

	private String to;
	
	private String subject;
	
	private Map<String, Object> model;

}
