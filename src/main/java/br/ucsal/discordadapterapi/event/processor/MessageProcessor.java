package br.ucsal.discordadapterapi.event.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.filter.Filter;
import br.ucsal.discordadapterapi.to.MessageTO;
import discord4j.core.object.entity.Message;

@Component
public class MessageProcessor extends Processor<MessageTO> {

	@Autowired
	public MessageProcessor(List<Filter<MessageTO>> filters) {
		super(filters);
	}

	public List<String> obterResposta(Message msg, Message msgAnterior) {
		
		MessageTO to = new MessageTO(msg, msgAnterior);
		try {

			verificarMsgNulaOuVazia(msg);
			verificarMsgBot(msg);

			return pipeline.process(to).getRetorno();

		} catch (BusinessException e) {
			return to.getRetorno();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return List.of("Ocorreu um erro! Digite \"menu\" e tente novamente.");
		}

	}

}
