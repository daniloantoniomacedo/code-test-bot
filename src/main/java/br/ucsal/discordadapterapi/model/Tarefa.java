package br.ucsal.discordadapterapi.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Tarefa {
	
	private Long id;
	private String titulo;
	private String descricao;
	private List<Teste> testes;
	
}
