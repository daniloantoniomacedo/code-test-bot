package br.ucsal.discordadapterapi.to.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TesteResponse {
	
	private Long id;
	private String nomeTeste;
	private String entrada;
	private String saida;
	private boolean flagExibir;

}
