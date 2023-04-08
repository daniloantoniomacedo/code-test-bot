package br.ucsal.discordadapterapi.event.listner.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.event.EventListener;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

@Component
public class MessageCreateListener implements EventListener<MessageCreateEvent> {
	
	@Autowired
	private MessageProcessor messageProcessor;

	@Override
	public Class<MessageCreateEvent> getEventType() {
		return MessageCreateEvent.class;
	}

	@Override
	public Mono<Void> execute(final MessageCreateEvent event) {
		Message msg = event.getMessage();
		String resposta = messageProcessor.obterResposta(msg);
		return Mono.just(msg).flatMap(Message::getChannel)
							 .flatMap(channel -> resposta.isEmpty() ? Mono.empty() : channel.createMessage(resposta))
							 .then();
	}
	
}
