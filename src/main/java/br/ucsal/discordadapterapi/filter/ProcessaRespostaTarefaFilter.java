package br.ucsal.discordadapterapi.filter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ucsal.discordadapterapi.service.RespostaService;
import br.ucsal.discordadapterapi.service.UsuarioService;
import br.ucsal.discordadapterapi.to.MessageTO;
import br.ucsal.discordadapterapi.to.response.UsuarioResponse;
import br.ucsal.discordadapterapi.util.Constantes;
import discord4j.core.object.entity.Message;

@Component
public class ProcessaRespostaTarefaFilter implements Filter<MessageTO> {
	
	@Autowired
	private RespostaService respostaService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public MessageTO process(MessageTO to) {
		if (ehRespostaTarefa(to)) {
			Message msgAtual = to.getMsg();
			if(msgAtual.getAuthor().isPresent()) {
				Optional<UsuarioResponse> retornoObterUsuario = usuarioService.obterDadosUsuario(msgAtual.getAuthor().get());
				to.setRetorno(obterRetorno(to, retornoObterUsuario));
			}
		}
		return to;
	}

	private List<String> obterRetorno(MessageTO to, Optional<UsuarioResponse> retornoObterUsuario) {
		Message msgAtual = to.getMsg();
		List<String> retorno = Collections.emptyList();
		if(retornoObterUsuario.isPresent()) {
			retorno = respostaService.corrigirResposta(msgAtual.getContent(), obterNumeroTarefa(to.getMsgAnterior()), retornoObterUsuario.get().getId());
		} else {
			Optional<Long> retornoCadastro = usuarioService.cadastrarUsuario(msgAtual.getAuthor().get());
			if(retornoCadastro.isPresent()) {
				retorno = respostaService.corrigirResposta(msgAtual.getContent(), obterNumeroTarefa(to.getMsgAnterior()), retornoCadastro.get());
			}
		}
		return retorno;
	}
	
	private static boolean ehRespostaTarefa(MessageTO to) {
		return Objects.nonNull(to.getMsg()) && !to.getMsg().getContent().contains(Constantes.MENU) && Objects.nonNull(to.getMsgAnterior()) && Objects.nonNull(to.getMsgAnterior().getContent())
				&& to.getMsgAnterior().getContent().length() > 0 && to.getMsgAnterior().getContent().contains(Constantes.TAREFA);
	}

	private static Long obterNumeroTarefa(Message msg) {
		String conteudo = msg.getContent();
		int i = conteudo.indexOf(Constantes.TAREFA);
		int j = i + Constantes.TAREFA.length();
		return Long.valueOf(conteudo.subSequence(j, j + 2).toString().trim());
	}
	
}
