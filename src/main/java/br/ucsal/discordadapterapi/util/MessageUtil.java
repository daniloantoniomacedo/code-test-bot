package br.ucsal.discordadapterapi.util;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Flux;

public class MessageUtil {
	
	public static Message obterMsgAnterior(Message msg) {
		Message msgAnterior = msg;
		MessageChannel canal = msg.getChannel().block();
		Flux<Message> messageHistory = canal.getMessagesBefore(Snowflake.of(msg.getId().asString()));
		Optional<List<Message>> op = messageHistory.collectList().blockOptional();
		if(Objects.nonNull(op) && op.isPresent()) {
			List<Message> lista = op.get();
			if(Objects.nonNull(lista) && !lista.isEmpty()) {
				msgAnterior = lista.get(0);
			}
		}
		return msgAnterior;
	}

}
