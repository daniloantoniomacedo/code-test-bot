package br.ucsal.discordadapterapi.filter;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.enums.EmojiEnum;
import br.ucsal.discordadapterapi.service.TarefaService;
import br.ucsal.discordadapterapi.to.ReactionTO;
import br.ucsal.discordadapterapi.util.Constantes;
import discord4j.discordjson.json.EmojiData;

@Component
public class ProcessaOpcaoMenuTarefaFilter implements Filter<ReactionTO> {
	
	@Autowired
	private TarefaService tarefaService;

	@Override
	public ReactionTO process(ReactionTO to) {
		EmojiData emojiData = to.getEmoji().asEmojiData();

		if (ehMenuTarefa(to) && Objects.nonNull(emojiData)) {
			EmojiEnum emojiEnum = EmojiEnum.obterEmojiEnum(emojiData.name().get().substring(0, 1));
			to.setRetorno(tarefaService.obterMsgApresentacaoTarefaPorEmoji(emojiEnum));
		}

		return to;
	}
	
	private static boolean ehMenuTarefa(ReactionTO to) {
		return to.getMsg().getContent().contains(Constantes.MENU_TAREFAS);
	}
	
}
