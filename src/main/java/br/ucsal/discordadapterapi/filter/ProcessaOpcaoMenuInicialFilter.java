package br.ucsal.discordadapterapi.filter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.enums.EmojiEnum;
import br.ucsal.discordadapterapi.service.SubmissaoService;
import br.ucsal.discordadapterapi.service.TarefaService;
import br.ucsal.discordadapterapi.to.ReactionTO;
import br.ucsal.discordadapterapi.util.Constantes;
import discord4j.core.object.entity.User;
import discord4j.discordjson.json.EmojiData;

@Component
public class ProcessaOpcaoMenuInicialFilter implements Filter<ReactionTO> {

	@Autowired
	private TarefaService tarefaService;

	@Autowired
	private SubmissaoService submissaoService;

	@Override
	public ReactionTO process(ReactionTO to) {
		EmojiData emojiData = to.getEmoji().asEmojiData();

		if (ehMenuInicial(to) && Objects.nonNull(emojiData)) {

			EmojiEnum emojiEnum = EmojiEnum.obterEmojiEnum(emojiData.name().get().substring(0, 1));
			
			if(EmojiEnum.OPCAO_1.equals(emojiEnum)) {
				to.setRetorno(List.of(tarefaService.obterMenuTarefas()));
			} else if(EmojiEnum.OPCAO_2.equals(emojiEnum)) {
				Optional<User> op = to.getMsgAnterior().getAuthor();
				if(op.isPresent()) {
					to.setRetorno(List.of(submissaoService.obterSubmissoes(op.get())));
				}
			} else if(EmojiEnum.OPCAO_3.equals(emojiEnum)) {
				to.setRetorno(List.of(apresentarInstrucoes()));
			}

		}

		return to;
	}

	private static boolean ehMenuInicial(ReactionTO to) {
		return to.getMsg().getContent().contains(Constantes.MENU_INICIAL);
	}
	
	private static String apresentarInstrucoes() {
		StringBuilder sb = new StringBuilder();
		sb.append("Para interagir com o BOT digite **menu** e siga as instruções.");
		return sb.toString();
	}

}
