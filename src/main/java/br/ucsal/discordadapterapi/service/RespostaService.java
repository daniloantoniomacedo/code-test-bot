package br.ucsal.discordadapterapi.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.http.client.CodeTestApiClientService;
import br.ucsal.discordadapterapi.to.request.RespostaRequest;
import br.ucsal.discordadapterapi.to.response.CasosTesteResponse;
import br.ucsal.discordadapterapi.to.response.ResultadoTarefaResponse;
import br.ucsal.discordadapterapi.util.Constantes;

@Service
public class RespostaService {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private CodeTestApiClientService codeTestApiClient;
	
	@Autowired
	private TarefaService tarefaService;

	public List<String> corrigirResposta(String resposta, Long idTarefa, Long idUsuario) {
		try {
			RespostaRequest request = obterRespostaRequest(resposta, idTarefa, idUsuario);
			Optional<ResultadoTarefaResponse> idResultado = codeTestApiClient.enviarResposta(request, tokenService.obterToken());
			if (idResultado.isPresent()) {
				Optional<ResultadoTarefaResponse> resultado = codeTestApiClient.obterResultadoPorId(idResultado.get().getId(),
						tokenService.obterToken());
				if (resultado.isPresent()) {
					String respCorrigida = obterCorrecaoTarefa(resultado.get());
					String feedBack = obterFeedback(resultado.get());
					String tarefaAposFeedBack = obterTarefaAposFeedBack(resultado.get(), idTarefa);
					return List.of(respCorrigida, feedBack, tarefaAposFeedBack);
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();

	}

	private static RespostaRequest obterRespostaRequest(String resposta, Long idTarefa, Long idUsuario) {
		RespostaRequest request = new RespostaRequest();
		request.setCodigo(resposta);
		request.setTarefaId(idTarefa);
		request.setUsuarioId(idUsuario);
		return request;
	}
	
	private static String obterCorrecaoTarefa(ResultadoTarefaResponse response) {
		StringBuilder sb = new StringBuilder();
		
		if(response.isCompile()) {
			sb.append("**").append(String.format("Você acertou: %.2f", response.getPorcentagem())).append("%").append("**").append(Constantes.ESCAPE);
			sb.append("> ").append("Testes realizados:").append(Constantes.ESCAPE);
			for(CasosTesteResponse teste : response.getTestes()) {
				if(response.getTestes().indexOf(teste) != 0) {
					sb.append("> ").append(Constantes.ESCAPE);
				}
				sb.append("> ").append("__").append(teste.getNome()).append("__").append(Constantes.ESCAPE);
				sb.append("> ").append(teste.isResultadoFinal() ? "Passou no teste :white_check_mark: " : "Falhou no teste :x: ").append(Constantes.ESCAPE);
				sb.append("> *Entrada:* ").append(teste.getEntrada()).append(Constantes.ESCAPE);
				sb.append("> *Saída esperada:* ").append(teste.getSaidaEsperada()).append(Constantes.ESCAPE);
				sb.append("> *Saída obtida:* ").append(teste.getSaidaObtida()).append(Constantes.ESCAPE);
			}
		} else {
			sb.append("Não compilou!").append(Constantes.ESCAPE);
			sb.append(response.getSaidaObtida().length() > 1950 ? response.getSaidaObtida().substring(0, 1950) : response.getSaidaObtida());
		}
		return sb.toString();
	}
	
	private static String obterFeedback(ResultadoTarefaResponse response) {
		if(Double.valueOf(100.0d).compareTo(response.getPorcentagem()) == 0) {
			return "Parabéns! Tente responder outra tarefa agora.";
		}
		return "Tente novamente!";
	}
	
	private String obterTarefaAposFeedBack(ResultadoTarefaResponse response, Long idTarefa) {
		if(Double.valueOf(100.0d).compareTo(response.getPorcentagem()) == 0) {
			return tarefaService.obterMsgApresentacaoTarefaAleatoria(idTarefa);
		}
		return tarefaService.obterMsgApresentacaoTarefaPorId(idTarefa);
	}

}
