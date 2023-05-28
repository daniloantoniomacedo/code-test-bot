package br.ucsal.discordadapterapi.event.processor;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.filter.Filter;
import br.ucsal.discordadapterapi.to.ReactionTO;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;

@Component
public class ReactionProcessor extends Processor<ReactionTO>{
	
	@Autowired
	public ReactionProcessor(List<Filter<ReactionTO>> filters) {
		super(filters);
	}

	public List<String> obterResposta(Message msg, Message msgAnterior, ReactionEmoji emoji) {
		try {
			
			verificarMsgNulaOuVazia(msg);
			verificarMsgNonBot(msg);
			
			ReactionTO to = new ReactionTO(msg, msgAnterior, emoji);
			return pipeline.process(to).getRetorno();

		} catch (BusinessException e) {
			e.printStackTrace();
			return Collections.emptyList();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return List.of("Ocorreu um erro! Digite \"menu\" e tente novamente.");
		}

	}

}
