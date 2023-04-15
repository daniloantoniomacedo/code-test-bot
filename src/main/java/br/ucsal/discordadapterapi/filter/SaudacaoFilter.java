package br.ucsal.discordadapterapi.filter;

import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.to.MessageTO;
import discord4j.core.object.entity.Message;

@Component
public class SaudacaoFilter implements Filter<MessageTO> {
	
	private String autor = "Desconhecido";

	@Override
	public MessageTO process(MessageTO to) {
		Message msg = to.getMsg();
		String conteudo = msg.getData().content();
		if (conteudo.startsWith("oi")) {
			msg.getAuthor().ifPresent(user -> autor = user.getUsername());
			to.setRetorno(String.format("Olá, %s!", autor));
		}

		return to;
	}

}
