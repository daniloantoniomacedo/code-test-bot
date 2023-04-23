package br.ucsal.discordadapterapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ucsal.discordadapterapi.exception.BusinessException;
import br.ucsal.discordadapterapi.http.client.CodeTestApiClientService;
import br.ucsal.discordadapterapi.to.request.CadastroUsuarioRequest;
import br.ucsal.discordadapterapi.to.response.UsuarioResponse;
import discord4j.core.object.entity.User;

@Service
public class UsuarioService {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private CodeTestApiClientService codeTestApiClient;

	public Optional<UsuarioResponse> obterDadosUsuario(User user) {

		if (Objects.isNull(user)) return Optional.empty();
		
		try {

			Optional<List<UsuarioResponse>> optional = codeTestApiClient.obterListaUsuarios(tokenService.obterToken());

			List<UsuarioResponse> listaUsuarios = new ArrayList<>();

			if (optional.isPresent()) {
				listaUsuarios = optional.get();
			}

			for (UsuarioResponse usuario : listaUsuarios) {
				if (usuario.getLogin().equals(user.getDiscriminator())) {
					return Optional.of(usuario);
				}
			}

		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public Optional<Long> cadastrarUsuario(User user){
		
		if (Objects.isNull(user)) return Optional.empty();
		
		try {
			Optional<UsuarioResponse> optional = codeTestApiClient.cadastrarUsuario(obterCadastroUsuarioRequest(user), tokenService.obterToken());
			if (optional.isPresent()) {
				Optional.ofNullable(optional.get().getId());
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
	private static CadastroUsuarioRequest obterCadastroUsuarioRequest(User user) {
		CadastroUsuarioRequest request = new CadastroUsuarioRequest();
		if(!user.getUserData().email().isAbsent()) {
			user.getUserData().email().get().ifPresent(email -> request.setEmail(email));
		}
		request.setNome(user.getUsername());
		request.setLogin(user.getDiscriminator());
		return request;
	}

}
