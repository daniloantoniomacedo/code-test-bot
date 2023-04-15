package br.ucsal.discordadapterapi.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RedisTO {
	
	private String chave;
	private Object valor;

}
