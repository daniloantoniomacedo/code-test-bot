package br.ucsal.discordadapterapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.http.client.CodeTestApiClientService;
import br.ucsal.discordadapterapi.to.request.RespostaRequest;
import br.ucsal.discordadapterapi.to.response.ResultadoTarefaResponse;
import br.ucsal.discordadapterapi.util.Constantes;

@Service
public class RespostaService {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private CodeTestApiClientService codeTestApiClient;

	public String corrigirResposta(String resposta, Long idTarefa, Long idUsuario) {
		try {
			RespostaRequest request = obterRespostaRequest(resposta, idTarefa, idUsuario);
			Optional<ResultadoTarefaResponse> idResultado = codeTestApiClient.enviarResposta(request, tokenService.obterToken());
			if (idResultado.isPresent()) {
				Optional<ResultadoTarefaResponse> resultado = codeTestApiClient.obterResultadoPorId(idResultado.get().getId(),
						tokenService.obterToken());
				if (resultado.isPresent()) {
					return resultado.get().getSaidaObtida();
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return Constantes.EMPTY_STRING;

	}

	private static RespostaRequest obterRespostaRequest(String resposta, Long idTarefa, Long idUsuario) {
		RespostaRequest request = new RespostaRequest();
		request.setCodigo(resposta);
		request.setTarefaId(idTarefa);
		request.setUsuarioId(idUsuario);
		return request;
	}
	
	private static String obterRetorno(ResultadoTarefaResponse response) {
		StringBuilder sb = new StringBuilder();
		
		if(response.isCompile()) {
			sb.append("");
		} else {
			
		}
		return sb.toString();
	}

}
