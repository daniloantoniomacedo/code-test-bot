package br.ucsal.discordadapterapi.to.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TarefaResponse {
	
	private Long id;
	private String titulo;
	private String descricao;
	private List<TesteResponse> testes;
	
}
