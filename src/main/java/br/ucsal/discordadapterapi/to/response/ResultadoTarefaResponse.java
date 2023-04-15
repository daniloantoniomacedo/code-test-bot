package br.ucsal.discordadapterapi.to.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResultadoTarefaResponse {
	
	private Long id;
	private String saidaObtida;
	private boolean compile;
	private Double porcentagem;
	private Long respostaId;
	private List<CasosTesteResponse> testes;

}
