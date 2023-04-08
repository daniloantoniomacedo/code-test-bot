package br.ucsal.discordadapterapi.filter;

import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.to.DadosTO;
import discord4j.core.object.entity.Message;

@Component
public class SaudacaoFilter implements Filter<DadosTO> {
	
	private String autor = "Desconhecido";

	@Override
	public DadosTO process(DadosTO to) {
		Message msg = to.getMessage();
		String conteudo = msg.getData().content();
		if (conteudo.startsWith("oi")) {
			msg.getAuthor().ifPresent(user -> autor = user.getUsername());
			to.setRetorno(String.format("Olá, %s!", autor));
		}

		return to;
	}

}
