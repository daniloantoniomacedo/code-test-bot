package br.ucsal.discordadapterapi.event.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.filter.Filter;
import br.ucsal.discordadapterapi.to.MessageTO;
import br.ucsal.discordadapterapi.util.Constantes;
import discord4j.core.object.entity.Message;

@Component
public class MessageProcessor extends Processor<MessageTO> {

	@Autowired
	public MessageProcessor(List<Filter<MessageTO>> filters) {
		super(filters);
	}

	public String obterResposta(Message msg) {
		try {

			verificarMsgNulaOuVazia(msg);
			verificarMsgBot(msg);

			MessageTO to = new MessageTO(msg);
			return pipeline.process(to).getRetorno();

		} catch (BusinessException e) {
			System.out.println(e.getMessage());
			return Constantes.EMPTY_STRING;
		}

	}

}
