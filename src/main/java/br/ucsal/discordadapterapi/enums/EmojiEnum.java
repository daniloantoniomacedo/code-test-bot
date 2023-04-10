package br.ucsal.discordadapterapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmojiEnum {
	
	OPCAO_1(1, "one"),
	OPCAO_2(2, "two"),
	OPCAO_3(3, "three"),
	OPCAO_4(4, "four"),
	OPCAO_5(5, "five"),
	OPCAO_6(6, "six"),
	OPCAO_7(7, "seven"),
	OPCAO_8(8, "eight"),
	OPCAO_9(9, "nine");
	
	private Integer numero;
	private String nome;
	
	public String obterCodigo() {
		return ":" + this.nome + ":";
	}
	
	public static EmojiEnum obterEmojiEnum(String name) {
		for(EmojiEnum emojiEnum: EmojiEnum.values()) {
			if(String.valueOf(emojiEnum.getNumero()).equals(name)) {
				return emojiEnum;
			}
		}
		return null;
	}
	
	
	public static EmojiEnum obterEmojiEnum(Integer numero) {
		for(EmojiEnum emojiEnum: EmojiEnum.values()) {
			if(emojiEnum.getNumero().equals(numero)) {
				return emojiEnum;
			}
		}
		return null;
	}
	
}
