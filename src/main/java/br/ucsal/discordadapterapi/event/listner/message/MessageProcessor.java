package br.ucsal.discordadapterapi.event.listner.message;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.filter.Filter;
import br.ucsal.discordadapterapi.pipe.Pipeline;
import br.ucsal.discordadapterapi.to.DadosTO;
import br.ucsal.discordadapterapi.util.Constantes;
import discord4j.core.object.entity.Message;

@Component
public class MessageProcessor {

	private Pipeline<DadosTO> pipeline;

	@Autowired
	public MessageProcessor(List<Filter<DadosTO>> filters) {
		this.pipeline = new Pipeline<DadosTO>(filters);
	}

	public String obterResposta(Message msg) {
		try {

			verificarMsgNulaOuVazia(msg);
			verificarMsgBot(msg);

			DadosTO to = new DadosTO(msg);
			return pipeline.process(to).getRetorno();

		} catch (BusinessException e) {
			return Constantes.EMPTY_STRING;
		}

	}

	private static void verificarMsgBot(Message msg) throws BusinessException {
		Boolean isBot = msg.getAuthor().map(user -> user.isBot()).orElse(false);
		if (isBot) {
			throw new BusinessException("Msg do bot");
		}
	}

	private static void verificarMsgNulaOuVazia(Message msg) throws BusinessException {
		if (Objects.isNull(msg) || Objects.isNull(msg.getData()) || Objects.isNull(msg.getData().content())) {
			throw new BusinessException("Msg do bot");
		}
	}

}
