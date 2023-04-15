package br.ucsal.discordadapterapi.event.listner.message;

import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.event.EventListener;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

@Component
public class MessageUpdateEventListener implements EventListener<MessageUpdateEvent> {

	@Override
	public Class<MessageUpdateEvent> getEventType() {
		return MessageUpdateEvent.class;
	}

	@Override
	public Mono<Void> execute(MessageUpdateEvent event) {
		return Mono.just(event)
				   .filter(MessageUpdateEvent::isContentChanged)
				   .flatMap(MessageUpdateEvent::getMessage)
				   .flatMap(Message::getChannel)
				   .flatMap(channel -> channel.createMessage("Mensagens alteradas são desconsideradas pelo bot."))
				   .then();
	}

}
