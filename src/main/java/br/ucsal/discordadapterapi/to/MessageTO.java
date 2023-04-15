package br.ucsal.discordadapterapi.to;

import br.ucsal.discordadapterapi.util.Constantes;
import discord4j.core.object.entity.Message;
import lombok.Data;

@Data
public class MessageTO {
	
	private Message msg;
	private Message msgAnterior;
	private String retorno = Constantes.EMPTY_STRING;
	
	public MessageTO(Message msg) {
		this.msg = msg;
	}
	public MessageTO(Message msg, Message msgAnterior) {
		this.msg = msg;
		this.msgAnterior = msgAnterior;
	}

}
