package br.ucsal.discordadapterapi.event.listner.reaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.event.EventListener;
import br.ucsal.discordadapterapi.event.processor.ReactionProcessor;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

@Component
public class ReactionAddEventListner implements EventListener<ReactionAddEvent>{
	
	@Autowired
	private ReactionProcessor reactionProcessor;
	
	@Override
	public Class<ReactionAddEvent> getEventType() {
		return ReactionAddEvent.class;
	}

	@Override
	public Mono<Void> execute(ReactionAddEvent event) {
		String resposta = reactionProcessor.obterResposta(event.getMessage().block(), event.getEmoji());
		return Mono.just(event).flatMap(ReactionAddEvent::getMessage)
				   			   .flatMap(Message::getChannel)
				   			   .flatMap(channel -> resposta.isEmpty() ? Mono.empty() : channel.createMessage(resposta))
				   			   .then();
	}

}
