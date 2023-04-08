package br.ucsal.discordadapterapi.event.listner.guild;

import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.event.EventListener;
import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.object.entity.Member;
import reactor.core.publisher.Mono;

@Component
public class MemberJoinEventListner implements EventListener<MemberJoinEvent>{
	
	@Override
	public Class<MemberJoinEvent> getEventType() {
		return MemberJoinEvent.class;
	}

	@Override
	public Mono<Void> execute(final MemberJoinEvent event) {
		Member member = event.getMember();
		return Mono.just(member.getPrivateChannel()
			       .flatMap(channel -> channel.createMessage("Bem-vindo ao nosso canal privado!"))).then();
	}
	 
}
