package br.ucsal.discordadapterapi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.http.client.CodeTestApiClientService;
import br.ucsal.discordadapterapi.to.response.SubmissaoResponse;
import br.ucsal.discordadapterapi.to.response.UsuarioResponse;
import br.ucsal.discordadapterapi.util.Constantes;
import br.ucsal.discordadapterapi.util.DataUtil;
import discord4j.core.object.entity.User;

@Service
public class SubmissaoService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CodeTestApiClientService codeTestApiClient;

	@Autowired
	private TokenService tokenService;

	public List<String> obterSubmissoes(User user) {
		List<String> listaSubmissoes = new ArrayList<>();
		Optional<UsuarioResponse> op = usuarioService.obterDadosUsuario(user);
		if (op.isPresent()) {
			listaSubmissoes = obterRetornoSubmissoes(listaSubmissoes, op.get().getId());
		}
		return listaSubmissoes;
	}

	private List<String> obterRetornoSubmissoes(List<String> listaSubmissoes, Long idUsuario) {
		try {
			Optional<List<SubmissaoResponse>> op = codeTestApiClient.obterSubmissoes(idUsuario,
					tokenService.obterToken());
			if (op.isPresent()) {
				listaSubmissoes = montarRetornoSubmissoes(listaSubmissoes, op.get());
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		return listaSubmissoes;
	}

	private static List<String> montarRetornoSubmissoes(List<String> listaSubmissoes, List<SubmissaoResponse> lista) {
		
		if (lista.isEmpty()) {
			listaSubmissoes.add("Nenhum submissão encontrada. Submeta uma resposta para uma tarefa.");
		} else {
			listaSubmissoes.add("*Submissões:*");
			Collections.sort(lista, (sub1, sub2) -> sub2.getDataEnvio().compareTo(sub1.getDataEnvio()));
			for (SubmissaoResponse sub : lista) {
				StringBuilder sb = new StringBuilder();
				sb.append("Tarefa: ").append(sub.getTarefa().getTitulo()).append(Constantes.ESCAPE);
				sb.append("Instução: ").append(sub.getTarefa().getDescricao()).append(Constantes.ESCAPE);
				sb.append("Percentual de acerto: ").append(String.format("%.2f", sub.getPorcentagemAcerto())).append("%").append(Constantes.ESCAPE);
				sb.append("Resposta: ").append(Constantes.ESCAPE);
				sb.append("Envio: ").append(DataUtil.formatarLocalDateTime(sub.getDataEnvio(), DataUtil.DATA_HORA_BR_FORMAT)).append(Constantes.ESCAPE);
				sb.append("Código: ").append("`").append(sub.getCodigo()).append("`").append(Constantes.ESCAPE);
				sb.append(Constantes.ESCAPE);
				
				if(sb.length() >= 1500) {
					sb.append("Obs.: Parte do código dessa submissão foi suprimido devido a limitação de caracteres do Discord.");
					listaSubmissoes.add(sb.toString());
					break;
				}
				
				listaSubmissoes.add(sb.toString());
			}

		}

		return listaSubmissoes;
	}

}
