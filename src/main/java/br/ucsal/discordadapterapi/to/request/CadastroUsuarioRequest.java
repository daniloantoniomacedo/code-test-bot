package br.ucsal.discordadapterapi.to.request;

import br.ucsal.discordadapterapi.util.Constantes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CadastroUsuarioRequest {

	private String nome;
	private String login;
	private String senha = Constantes.EMPTY_STRING;
	private String email = Constantes.EMAIL_PADRAO;
	private boolean flagAtivo = true;
	private Long perfilId = Constantes.PERFIL_ALUNO;

}
