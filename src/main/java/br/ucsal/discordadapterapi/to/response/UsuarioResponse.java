package br.ucsal.discordadapterapi.to.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioResponse {
	
    private Long id;
    private String nome;
    private String login;
    private Boolean flagAtivo;

}
