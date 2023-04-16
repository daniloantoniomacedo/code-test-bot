package br.ucsal.discordadapterapi.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ucsal.discordadapterapi.enums.EmojiEnum;
import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.http.client.CodeTestApiClientService;
import br.ucsal.discordadapterapi.to.response.TarefaResponse;
import br.ucsal.discordadapterapi.to.response.TesteResponse;
import br.ucsal.discordadapterapi.util.Constantes;

@Service
public class TarefaService {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private CodeTestApiClientService codeTestApiClient;
	
	private static LinkedHashMap<EmojiEnum, TarefaResponse> tarefaMap = new LinkedHashMap<>();
	
	public String obterMenuTarefas() {
		
		try {
			List<TarefaResponse> tarefas = codeTestApiClient.obterTarefas(tokenService.obterToken());
			mapearTarefas(tarefas);
			return montarMenuTarefas();
		} catch (RuntimeException | BusinessException e) {
			e.printStackTrace();
			return Constantes.MSG_ERRO;
		}
		
	}
	
	public String obterMsgApresentacaoTarefaPorEmoji(EmojiEnum emojiEnum) {
		try {
			Long id = obterIdTarefaPorEmoji(emojiEnum);
			TarefaResponse tarefa = codeTestApiClient.obterTarefaPeloId(id, tokenService.obterToken());
			return montarApresentacaoTarefas(tarefa, emojiEnum);
		} catch (BusinessException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}
	
	private Long obterIdTarefaPorEmoji(EmojiEnum emojiEnum) throws BusinessException {
		TarefaResponse tarefa = tarefaMap.get(emojiEnum);
		
		if (Objects.isNull(tarefa)) {
			throw new BusinessException(Constantes.MSG_ERRO_TAREFA_NAO_ENCONTRADA);
		} else {
			return tarefa.getId();
		}
	}
	
	private static void mapearTarefas(List<TarefaResponse> tarefas) {
		if(Objects.isNull(tarefas) || tarefas.isEmpty()) {
			return;
		}
		
		for(TarefaResponse tarefa : tarefas) {
			int i = tarefas.indexOf(tarefa) + 1;
			EmojiEnum emojiEnum = EmojiEnum.obterEmojiEnum(i);
			if(Objects.nonNull(emojiEnum)) {
				tarefaMap.put(emojiEnum, tarefa);
			}
		}
	}
	
	private static String montarMenuTarefas() {
		StringBuilder sb = new StringBuilder();
		sb.append("--------- ").append(Constantes.MENU_TAREFAS).append(" ---------").append(Constantes.ESCAPE);
		
		for(Entry<EmojiEnum, TarefaResponse> entry : tarefaMap.entrySet()) {
			sb.append(entry.getKey().obterCodigo()).append(" - ").append(entry.getValue().getTitulo()).append(Constantes.ESCAPE);
		}
		
		sb.append("Reaja com o emoji correspondente para selecionar uma opção.").append(Constantes.ESCAPE);
		sb.append("-------------------------------------").append(Constantes.ESCAPE);
		return sb.toString();
	}
	
	private static String montarApresentacaoTarefas(TarefaResponse tarefa, EmojiEnum emojiEnum) {
		StringBuilder sb = new StringBuilder();
		sb.append("**").append(tarefa.getTitulo()).append("**").append(Constantes.ESCAPE);
		sb.append(tarefa.getDescricao()).append(Constantes.ESCAPE);
		if(Objects.nonNull(tarefa.getTestes()) && !tarefa.getTestes().isEmpty()) {
			sb.append("> ").append("Exemplos: ").append(Constantes.ESCAPE);
			for(TesteResponse teste : tarefa.getTestes()) {
				if(teste.isFlagExibir()) {
					sb.append("> ").append("*Entrada:* ").append(teste.getEntrada()).append(Constantes.ESCAPE);
					sb.append("> ").append("*Saída:* ").append(teste.getSaida()).append(Constantes.ESCAPE);
					sb.append("> ").append(Constantes.ESCAPE);
				}
			}
		}
		sb.append(String.format("Resolva a " + Constantes.TAREFA + " %d na sua IDE favorita, copie a classe *Main* e cole no chat.", emojiEnum.getNumero())).append(Constantes.ESCAPE);
		sb.append("__Obs.: o nome da classe deve ser *Main*__");
		return sb.toString();
	}
	
}
