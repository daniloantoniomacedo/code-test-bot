package br.ucsal.discordadapterapi.event.processor;

import java.util.List;
import java.util.Objects;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.filter.Filter;
import br.ucsal.discordadapterapi.pipe.Pipeline;
import discord4j.core.object.entity.Message;

public abstract class Processor<T> {
	
	Pipeline<T> pipeline;
	
	public Processor(List<Filter<T>> filters){
		this.pipeline = new Pipeline<T>(filters);
	}
	
	static void verificarMsgBot(Message msg) throws BusinessException {
		Boolean isBot = msg.getAuthor().map(user -> user.isBot()).orElse(false);
		if (isBot) {
			throw new BusinessException("Msg do bot");
		}
	}
	
	static void verificarMsgNonBot(Message msg) throws BusinessException {
		Boolean nonBot = msg.getAuthor().map(user -> !user.isBot()).orElse(false);
		if (nonBot) {
			throw new BusinessException("Msg NAO eh do bot");
		}
	}

	static void verificarMsgNulaOuVazia(Message msg) throws BusinessException {
		if (Objects.isNull(msg) || Objects.isNull(msg.getData()) || Objects.isNull(msg.getData().content())) {
			throw new BusinessException("Msg vazia ou nula");
		}
	}

}
