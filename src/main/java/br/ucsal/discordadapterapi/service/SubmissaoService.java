package br.ucsal.discordadapterapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.http.client.CodeTestApiClientService;
import br.ucsal.discordadapterapi.to.response.SubmissaoResponse;
import br.ucsal.discordadapterapi.to.response.UsuarioResponse;
import br.ucsal.discordadapterapi.util.Constantes;
import discord4j.core.object.entity.User;

@Service
public class SubmissaoService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CodeTestApiClientService codeTestApiClient;

	@Autowired
	private TokenService tokenService;

	public String obterSubmissoes(User user) {
		String retorno = Constantes.MSG_ERRO;
		Optional<UsuarioResponse> op = usuarioService.obterDadosUsuario(user);
		if (op.isPresent()) {
			retorno = obterRetornoSubmissoes(retorno, op.get().getId());
		}
		return retorno;
	}

	private String obterRetornoSubmissoes(String retorno, Long idUsuario) {
		try {
			Optional<List<SubmissaoResponse>> op = codeTestApiClient.obterSubmissoes(idUsuario,
					tokenService.obterToken());
			if (op.isPresent()) {
				retorno = montarRetornoSubmissoes(op.get());
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		return retorno;
	}

	private static String montarRetornoSubmissoes(List<SubmissaoResponse> lista) {
		StringBuilder sb = new StringBuilder();

		if (lista.isEmpty()) {
			sb.append("Nenhum submissão encontrada. Submeta uma resposta para uma tarefa.");
		} else {
			sb.append("*Submissões:*").append(Constantes.ESCAPE);
			for (SubmissaoResponse sub : lista) { //TODO: ordenar lista do mais antigo para o mais novo
				sb.append("Tarefa: ").append(sub.getTarefa().getTitulo()).append(Constantes.ESCAPE);
				sb.append("Instução: ").append(sub.getTarefa().getDescricao()).append(Constantes.ESCAPE);
				sb.append("Percentual de acerto: ").append(String.format("%.2f", sub.getPorcentagemAcerto())).append("%").append(Constantes.ESCAPE);
				sb.append("Resposta: ").append(Constantes.ESCAPE);
				sb.append("Envio: ").append(sub.getDataEnvio()).append(Constantes.ESCAPE);
				sb.append("Código: ").append("`").append(sub.getCodigo()).append("`").append(Constantes.ESCAPE);
				sb.append(Constantes.ESCAPE);
				
				if(sb.length() >= 1500) {
					sb.append("Obs.: Algumas submissões mais antigas foram suprimidas devido a limitação de caracteres do Discord.");
					break;
				}
			}

		}

		return sb.toString();
	}

}
