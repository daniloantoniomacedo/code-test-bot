package br.ucsal.discordadapterapi.to;

import br.ucsal.discordadapterapi.util.Constantes;
import discord4j.core.object.entity.Message;
import lombok.Data;

@Data
public class MessageTO {
	
	private Message message;
	private String retorno = Constantes.EMPTY_STRING;
	
	public MessageTO(Message msg) {
		this.message = msg;
	}

}
