package br.ucsal.discordadapterapi.event.listner.reaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.event.EventListener;
import br.ucsal.discordadapterapi.event.processor.ReactionProcessor;
import br.ucsal.discordadapterapi.util.MessageUtil;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Flux;
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
		Message msg = event.getMessage().block();
		MessageChannel ch = msg.getChannel().block();
		Message msgAnterior = MessageUtil.obterMsgAnterior(msg);
		
		return Flux.fromIterable(reactionProcessor.obterResposta(event.getMessage().block(), msgAnterior, event.getEmoji()))
				   .flatMap(r ->  r.isEmpty() ? Mono.empty() : ch.createMessage(r)).then();
		
	}

}
