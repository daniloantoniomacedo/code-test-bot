package br.ucsal.discordadapterapi.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.service.TarefaService;
import br.ucsal.discordadapterapi.to.MessageTO;
import br.ucsal.discordadapterapi.util.Constantes;

@Component
public class ProcessaEscolhaTarefaFilter implements Filter<MessageTO> {
	
	@Autowired
	private TarefaService tarefaService;

	@Override
	public MessageTO process(MessageTO to) {
		if(ehMenuTarefa(to)) {
			
			Long id = null;
			try {
				id = Long.valueOf(to.getMsg().getContent());
			} catch (NumberFormatException e) {
				to.setRetorno("Digite apenas o número da tarefa escolhida.");
				return to;
			}
			
			to.setRetorno(tarefaService.obterMsgApresentacaoTarefaPorId(id));
		}
		return to;
	}
	
	private static boolean ehMenuTarefa(MessageTO to) {
		return to.getMsgAnterior().getAuthor().get().isBot() && 
			   to.getMsgAnterior().getContent().contains(Constantes.MENU_TAREFAS);
	}
	
}
