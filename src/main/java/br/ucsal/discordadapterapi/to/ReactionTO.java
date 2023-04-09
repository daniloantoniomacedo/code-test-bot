package br.ucsal.discordadapterapi.to;

import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ReactionTO extends MessageTO {
	
	ReactionEmoji emoji;
	
	public ReactionTO(Message msg, ReactionEmoji emoji) {
		super(msg);
		this.emoji = emoji;
	}


}
