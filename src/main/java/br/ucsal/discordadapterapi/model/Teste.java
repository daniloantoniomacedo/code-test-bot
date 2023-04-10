package br.ucsal.discordadapterapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Teste {
	
	private Long id;
	private String nomeTeste;
	private String entrada;
	private String saida;
	private boolean flagExibir;

}
