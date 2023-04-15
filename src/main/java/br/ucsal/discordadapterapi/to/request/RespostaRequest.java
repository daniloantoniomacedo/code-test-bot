package br.ucsal.discordadapterapi.to.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RespostaRequest {
	
	private String codigo;
    private Long usuarioId;
    private Long tarefaId;

}
