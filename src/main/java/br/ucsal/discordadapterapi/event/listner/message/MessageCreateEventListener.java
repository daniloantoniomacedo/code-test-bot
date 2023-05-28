package br.ucsal.discordadapterapi.event.listner.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.event.EventListener;
import br.ucsal.discordadapterapi.event.processor.MessageProcessor;
import br.ucsal.discordadapterapi.util.MessageUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MessageCreateEventListener implements EventListener<MessageCreateEvent> {
	
	@Autowired
	private MessageProcessor messageProcessor;

	@Override
	public Class<MessageCreateEvent> getEventType() {
		return MessageCreateEvent.class;
	}

	@Override
	public Mono<Void> execute(final MessageCreateEvent event) {
		
		Message msg = event.getMessage();
		Message msgAnterior = MessageUtil.obterMsgAnterior(msg);
		
		MessageChannel ch = msg.getChannel().block();
		return Flux.fromIterable(messageProcessor.obterResposta(msg, msgAnterior)).flatMap(r ->  r.isEmpty() ? Mono.empty() : ch.createMessage(r)).then();
		
	}
	
}
