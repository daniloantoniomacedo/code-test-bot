package br.ucsal.discordadapterapi.to.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubmissaoResponse {
	
	 private Long id;
	 private String codigo;
	 private LocalDateTime dataEnvio;
	 private Double porcentagemAcerto;
	 private TarefaResponse tarefa;

}
