package br.ucsal.discordadapterapi.to;

import java.util.Collections;
import java.util.List;

import discord4j.core.object.entity.Message;
import lombok.Data;

@Data
public class MessageTO {
	
	private Message msg;
	private Message msgAnterior;
	private List<String> retorno = Collections.emptyList();
	
	public MessageTO(Message msg) {
		this.msg = msg;
	}
	public MessageTO(Message msg, Message msgAnterior) {
		this.msg = msg;
		this.msgAnterior = msgAnterior;
	}

}
