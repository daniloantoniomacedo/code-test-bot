package br.ucsal.discordadapterapi.filter;

import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.enums.EmojiEnum;
import br.ucsal.discordadapterapi.to.MessageTO;
import br.ucsal.discordadapterapi.util.Constantes;
import discord4j.core.object.entity.Message;

@Component
public class ApresentaMenuInicialFilter implements Filter<MessageTO> {
	
	@Override
	public MessageTO process(MessageTO to) {
		Message msg = to.getMsg();
		String conteudo = msg.getData().content();
		if(conteudo.startsWith("menu")) {
			StringBuilder sb = new StringBuilder();
			sb.append("--------- ").append(Constantes.MENU_INICIAL).append(" ---------\n");
			sb.append(EmojiEnum.OPCAO_1.obterCodigo()).append(" - Tarefas \n");
			sb.append(EmojiEnum.OPCAO_2.obterCodigo()).append(" - Minhas submissões \n");
			sb.append("Reaja com o emoji correspondente para selecionar uma opção.\n");
			sb.append("------------------------------------\n");
			to.setRetorno(sb.toString());
		}
		return to;
	}

}
