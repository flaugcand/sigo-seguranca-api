package br.com.sigo.indtexbr.model.dto;

import br.com.sigo.indtexbr.model.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAutenticadoDTO {

	private Long id;

	private String nome;

}
