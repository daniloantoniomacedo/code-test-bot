package br.ucsal.discordadapterapi.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

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
			Optional<List<TarefaResponse>> op = codeTestApiClient.obterTarefas(tokenService.obterToken());
			if(op.isPresent()) {
				List<TarefaResponse> tarefas = op.get();
				return montarMenuTarefas(tarefas);
			}
		} catch (RuntimeException | BusinessException e) {
			e.printStackTrace();
		}
		return Constantes.MSG_ERRO;
	}
	
	public String obterMenuTarefasComEmoji() {
		
		try {
			Optional<List<TarefaResponse>> op = codeTestApiClient.obterTarefas(tokenService.obterToken());
			if(op.isPresent()) {
				List<TarefaResponse> tarefas = op.get();
				mapearTarefas(tarefas);
				return montarMenuTarefasComEmoji();
			}
		} catch (RuntimeException | BusinessException e) {
			e.printStackTrace();
		}
		return Constantes.MSG_ERRO;
	}
	
	public String obterMsgApresentacaoTarefaPorEmoji(EmojiEnum emojiEnum) {
		try {
			Long id = obterIdTarefaPorEmoji(emojiEnum);
			return obterMsgApresentacaoTarefaPorId(id);
		} catch (BusinessException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}
	
	public String obterMsgApresentacaoTarefaPorId(Long id) {
		try {
			TarefaResponse tarefa = codeTestApiClient.obterTarefaPeloId(id, tokenService.obterToken());
			return montarApresentacaoTarefa(tarefa, id);
		} catch (BusinessException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}
	
	public String obterMsgApresentacaoTarefaAleatoria(Long idTarefaCompleta) {
		List<Long> idsDisponiveis = obterIdsTarefasDisponiveis(); 
		Long idTarefaAleatoria = obterIdTarefaAleatoria(idTarefaCompleta, idsDisponiveis);
		return obterMsgApresentacaoTarefaPorId(idTarefaAleatoria);
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
	
	private static String montarMenuTarefasComEmoji() {
		StringBuilder sb = new StringBuilder();
		sb.append("--------- ").append(Constantes.MENU_TAREFAS).append(" ---------").append(Constantes.ESCAPE);
		
		for(Entry<EmojiEnum, TarefaResponse> entry : tarefaMap.entrySet()) {
			sb.append(entry.getKey().obterCodigo()).append(" - ").append(entry.getValue().getTitulo()).append(Constantes.ESCAPE);
		}
		
		sb.append("Digite o número correspondente para selecionar uma opção.").append(Constantes.ESCAPE);
		sb.append("-------------------------------------").append(Constantes.ESCAPE);
		return sb.toString();
	}
	
	private static String montarMenuTarefas(List<TarefaResponse> tarefas) {
		StringBuilder sb = new StringBuilder();
		sb.append("--------- ").append(Constantes.MENU_TAREFAS).append(" ---------").append(Constantes.ESCAPE);
		
		for(TarefaResponse tarefa : tarefas) {
			sb.append("#").append(tarefa.getId()).append(" - ").append(tarefa.getTitulo()).append(Constantes.ESCAPE);
		}
		
		sb.append("Digite o número correspondente para selecionar uma opção.").append(Constantes.ESCAPE);
		sb.append("-------------------------------------").append(Constantes.ESCAPE);
		return sb.toString();
	}
	
	private static String montarApresentacaoTarefa(TarefaResponse tarefa, Long id) {
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
		sb.append(String.format("Resolva a " + Constantes.TAREFA + " %d na sua IDE favorita, copie a classe *Main* e cole no chat.", id)).append(Constantes.ESCAPE);
		sb.append("__Obs.: o nome da classe deve ser *Main*__");
		return sb.toString();
	}
	
	
	private List<Long> obterIdsTarefasDisponiveis() {
		
		List<Long> idsList = new ArrayList<>();
		
		try {
			Optional<List<TarefaResponse>> op = codeTestApiClient.obterTarefas(tokenService.obterToken());
			if(op.isPresent()) {
				List<TarefaResponse> tarefas = op.get();
				for(TarefaResponse tarefa : tarefas) {
					idsList.add(tarefa.getId());
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return idsList;
	}
	
	private static Long obterIdTarefaAleatoria(Long excludedId, List<Long> idsAvailable) {
		
		if(idsAvailable.isEmpty()) {
			return 0L;
		}
		
        Random random = new Random();
        int randomIndex;

        do {
            randomIndex = random.nextInt(idsAvailable.size());
        } while (idsAvailable.get(randomIndex) == excludedId);

        return idsAvailable.get(randomIndex);
	}
	
}
