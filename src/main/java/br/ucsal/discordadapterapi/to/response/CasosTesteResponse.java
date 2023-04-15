package br.ucsal.discordadapterapi.to.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CasosTesteResponse {
	
	private Long id;
    private boolean resultadoFinal;
    private String entrada;
    private String saidaEsperada;
    private String saidaObtida;
    
}
