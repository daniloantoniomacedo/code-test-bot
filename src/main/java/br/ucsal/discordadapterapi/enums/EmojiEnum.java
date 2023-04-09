package br.ucsal.discordadapterapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmojiEnum {
	
	OPCAO_1(1, "one"),
	OPCAO_2(2, "tow"),
	OPCAO_3(3, "three");
	
	private Integer numero;
	private String nome;
	
	public String obterCodigo() {
		return ":" + this.nome + ":";
	}
	
	public static EmojiEnum obterEmojiEnum(String nome) {
		for(EmojiEnum emojiEnum: EmojiEnum.values()) {
			if(String.valueOf(emojiEnum.getNumero()).equals(nome)) {
				return emojiEnum;
			}
		}
		return null;
	}
	
}
