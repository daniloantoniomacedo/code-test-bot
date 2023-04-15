package br.ucsal.discordadapterapi.event.listner.message;

import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.event.EventListener;
import discord4j.core.event.domain.message.MessageDeleteEvent;
import reactor.core.publisher.Mono;

@Component
public class MessageDeleteEventListener implements EventListener<MessageDeleteEvent>{

	@Override
	public Class<MessageDeleteEvent> getEventType() {
		return MessageDeleteEvent.class;
	}

	@Override
	public Mono<Void> execute(MessageDeleteEvent event) {
		boolean ehMsgBot = event.getMessage().map(msg -> msg.getAuthor().get()).map(user -> user.isBot()).orElse(false);
		return Mono.just(event)
				.flatMap(MessageDeleteEvent::getChannel)			
				.flatMap(channel -> channel.createMessage(ehMsgBot ? "Não apague minhas mensagens!" : "Eu li o que você escreveu!"))
				.then();
	}

}
