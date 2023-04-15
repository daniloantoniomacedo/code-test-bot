package br.ucsal.discordadapterapi.event.listner.message;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.event.EventListener;
import br.ucsal.discordadapterapi.event.processor.MessageProcessor;
import discord4j.common.util.Snowflake;
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
		Message msgAnterior = obterMsgAnterior(msg);
		String resposta = messageProcessor.obterResposta(msg, msgAnterior);
		return Mono.just(msg).flatMap(Message::getChannel)
							 .flatMap(channel -> resposta.isEmpty() ? Mono.empty() : channel.createMessage(resposta))
							 .then();
	}

	private static Message obterMsgAnterior(Message msg) {
		Message msgAnterior = msg;
		MessageChannel canal = msg.getChannel().block();
		Flux<Message> messageHistory = canal.getMessagesBefore(Snowflake.of(msg.getId().asString()));
		Optional<List<Message>> op = messageHistory.collectList().blockOptional();
		if(op.isPresent()) {
			msgAnterior = op.get().get(0);
		}
		return msgAnterior;
	}
	
}
