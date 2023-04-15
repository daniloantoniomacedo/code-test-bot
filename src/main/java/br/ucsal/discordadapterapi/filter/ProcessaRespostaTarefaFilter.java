package br.ucsal.discordadapterapi.filter;

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
import discord4j.core.object.entity.User;

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
				Optional<UsuarioResponse> retornoObterUsuario = usuarioService.obterUsuario(to.getMsgAnterior().getAuthor().get());
				to.setRetorno(obterRetorno(to, retornoObterUsuario));
			}
		}
		return to;
	}

	private String obterRetorno(MessageTO to, Optional<UsuarioResponse> retornoObterUsuario) {
		Message msgAtual = to.getMsg();
		User user = to.getMsgAnterior().getAuthor().get();
		String retorno = Constantes.EMPTY_STRING;
		if(retornoObterUsuario.isPresent()) {
			retorno = respostaService.corrigirResposta(msgAtual.getContent(), obterNumeroTarefa(to.getMsgAnterior()), retornoObterUsuario.get().getId());
		} else {
			Optional<Long> retornoCadastro = usuarioService.cadastrarUsuario(user);
			if(retornoCadastro.isPresent()) {
				retorno = respostaService.corrigirResposta(msgAtual.getContent(), obterNumeroTarefa(to.getMsgAnterior()), retornoCadastro.get());
			}
		}
		return retorno;
	}
	
	private static boolean ehRespostaTarefa(MessageTO to) {
		return Objects.nonNull(to.getMsg()) && Objects.nonNull(to.getMsgAnterior()) && Objects.nonNull(to.getMsgAnterior().getContent())
				&& to.getMsgAnterior().getContent().length() > 0 && to.getMsgAnterior().getContent().contains(Constantes.TAREFA);
	}

	private static Long obterNumeroTarefa(Message msg) {
		String conteudo = msg.getContent();
		int i = conteudo.indexOf(Constantes.TAREFA);
		int j = i + Constantes.TAREFA.length();
		return Long.valueOf(conteudo.subSequence(j, j + 2).toString().trim());
	}
	
}
